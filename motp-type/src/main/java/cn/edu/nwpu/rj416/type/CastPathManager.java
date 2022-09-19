package cn.edu.nwpu.rj416.type;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;
import cn.edu.nwpu.rj416.type.util.TypeUtil;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


/**
 * 管理所有的MTypeCaster
 *
 * @author MilesLiu
 * Date 2020-03-14 16:57
 */
public class CastPathManager {

    private static final CastPath NULL_PATH = new CastPath(); //转换路径属性（记录使用的转换器）

    //获取CastPathManager类的实例（所有默认类型的转换器）
    public static final CastPathManager getInstance() {
        List<Class<?>> casterClasses = new ArrayList<>();
        casterClasses.addAll(Arrays.asList(DefaultCasters.DefaultCasterClasses));//将所有默认类型的转换器都添加进列表


        // TODO
//		if (Macaw.getClassSet() != null) {
//			List<Class<?>> allCasters = Macaw.getClassSet().select(
//					MClassFilterBySuperClass.get(MTypeCaster.class));
//			casterClasses.addAll(allCasters); //将classSet中属于转换器类的对象也添加进列表
//		}

        return new CastPathManager(casterClasses);
    }

    private List<CasterDetail> allCasters = null; //转型细节列表属性（记录使用转换器的转型细节）
    //	private Map<String, CasterGraphsNode> castGraphs = null;
    private AtomicReference<Map<String, CastPath>> castPathCache = new AtomicReference<>();//转换路径缓存

    //私有的有参构造函数，参数为一个存放了多个类型转换器Class对象的列表
    private CastPathManager(List<Class<?>> casterClasses) {
        super();
        List<CasterDetail> casterList = new ArrayList<>();
        Map<String, CastPath> castPathMap = new HashMap<>();

        /*
         * 遍历传参列表，将属于类型转换器的Class对象添加进转换路径缓存，并将其对应的转型细节添加进转型细节列表属性
         */
        for (Class<?> clazz : casterClasses) { //遍历列表中的Class对象
            if (clazz.isInterface()) { //不能是接口
                continue;
            }
            if (!MTypeCaster.class.isAssignableFrom(clazz)) { //要是MTypeCaster类对应的Class对象
                continue;
            }

            @SuppressWarnings("unchecked")
            Class<MTypeCaster<?, ?>> casterClass = (Class<MTypeCaster<?, ?>>) clazz;

            CasterDetail casterDetail = CasterDetail.create(casterClass);//根据转换器的Class对象创建转换细节对象
            casterList.add(casterDetail);

            CastPath cp = this.createCastPath(casterDetail.getCaster());
            String castKey = this.getCastPathKey(casterDetail.getFromType(), casterDetail.getToType());
            castPathMap.put(castKey, cp);//根据转换细节对象将其键值添加进转换路径Map中
        }

        this.sortCaster(casterList);

        this.allCasters = casterList;
        this.castPathCache.set(castPathMap);
    }

    //根据转换前后的类型获取转换路径的键（以-->连接两个类型名称的字符串）
    private String getCastPathKey(Type from, Type to) {
        return from.getTypeName() + "-->" + to.getTypeName();
    }

    /**
     * Map的Key为-->连接的转换两端类型名称 FromTypeName--ToTypeName
     */

    //根据转换前后的类型获取转换路径对象
    public CastPath getCastPath(Type from, Type to) {
        if (from == null || to == null) {
            return null;
        }

        String castKey = this.getCastPathKey(from, to); //根据转换前后的类型获取Map中的键

        CastPath cp = this.getCastPathFromCache(castKey); //根据键获取对应的转换路径对象的值
        if (cp != null) {
            if (cp.getCastPath() == null) {
                return null;
            }
            return cp; //获取的转换路径不空则直接返回
        }

        /*
         * 获取的转换路径为空，则从当前转换路径管理对象所管理的转换器细节列表中查找
         * 有无从指定的fromType到指定的toType的转换路径存在，若有，将其添加进
         * 路径缓存后返回，若无，将空路径添加进路径缓存后返回空
         */
        cp = this.searchCastPath(from, to);
        if (cp == null) {
            this.addCastPathToCache(castKey, NULL_PATH);
        } else {
            this.addCastPathToCache(castKey, cp);
        }
        return cp;
    }

    //从路径缓存Map中根据转换路径的键获取其具体对象值
    private CastPath getCastPathFromCache(String castKey) {
        CastPath cp = this.castPathCache.get().get(castKey);
        return cp;
    }

    //指定转换路径的键值对，将其添加进转换路径的缓存中
    private void addCastPathToCache(String castKey, CastPath cp) {
        Map<String, CastPath> oriMap = this.castPathCache.get(); //原本的缓存Map

        Map<String, CastPath> newMap = new HashMap<>(); //新的缓存Map
        newMap.putAll(oriMap); //将原本缓存中的内容全部添加到新Map中
        newMap.put(castKey, cp); //在新Map中添加要添加的键值对

        this.castPathCache.set(newMap); //设置新Map为缓存内容
    }

    //根据传入的若干转换器创建转换路径对象
    private CastPath createCastPath(MTypeCaster<?, ?>... caster) {
        CastPath cp = new CastPath();
        MTypeCaster<?, ?>[] path = new MTypeCaster<?, ?>[caster.length]; //创建传参转换器个数长度的转换器类型数组
        for (int i = 0; i < caster.length; i++) {
            path[i] = caster[i]; //依次将转换器对象添加进数组
        }
        cp.setCastPath(path); //设置空转换路径对象的转换路径属性为添加完成后的数组

        return cp;
    }

    /*
     * 根据传参中转换前后的类型，从转换路径管理器维护的转换细节数组中查找有无对应的转换器
     * 若有，根据转换细节获取相应的转换器后根据转换器创建转换路径返回，若无，返回空
     */
    private CastPath searchCastPath(Type from, Type to) {
        for (CasterDetail cd : this.allCasters) {
            if (!TypeUtil.isAssignableTo(from, cd.getFromType())) {
                continue;
            }
            if (!TypeUtil.isAssignableTo(to, cd.getToType())) {
                continue;
            } //from 与 to均可被CasterDetail对象的fromType和toType赋值相当于找到了对应的转换细节对象

            return this.createCastPath(cd.getCaster());
        }

        // TODO 需要用搜索图的方式找到paths（多步转换）

        return null;
    }

    //根据指定的比较器导出的顺序对转换细节列表进行排序
    private void sortCaster(List<CasterDetail> list) {
        list.sort(new Comparator<CasterDetail>() { //这样写等价于自定义实现了Comparator接口的比较器

            @Override
            //两个CasterDetail对象的比较方法
            public int compare(CasterDetail arg0, CasterDetail arg1) {
                if (arg0.getFromType() == arg1.getFromType()) {

                } else if (TypeUtil.isAssignableTo(arg0.getFromType(), arg1.getFromType())) {
                    return -1;
                } else if (TypeUtil.isAssignableTo(arg1.getFromType(), arg0.getFromType())) {
                    return 1;
                } else {
                    return 0;
                }

                if (arg0.getToType() == arg1.getToType()) {
                    return 0;
                } else if (TypeUtil.isAssignableTo(arg0.getToType(), arg1.getToType())) {
                    return -1;
                } else if (TypeUtil.isAssignableTo(arg1.getToType(), arg0.getToType())) {
                    return 1;
                } else {
                    return 0;
                }
            }

        });
    }
}
