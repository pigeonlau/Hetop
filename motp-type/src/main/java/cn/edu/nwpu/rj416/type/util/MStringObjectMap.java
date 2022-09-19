package cn.edu.nwpu.rj416.type.util;



import cn.edu.nwpu.rj416.type.Macaw;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;

import java.util.*;

public class MStringObjectMap implements Map<String, Object>{

	private Map<String, Object> map = new HashMap<>();
	private Handler handler = null;

	
	public MStringObjectMap(Handler handler) {
		super();
		this.handler = handler;
	}

	public MStringObjectMap() {
		super();
	}

	@Override
	public Object put(String key, Object value) {
		return this.map.put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public MStringObjectMap append(String key, Object value) {
		Object exist = this.map.get(key);
		if (exist == null) {
			this.put(key, value);
			return this;
		}
		
		List<Object> list = null;
		if (exist instanceof List) {
			list = (List<Object>)exist;
		} else {
			list = new ArrayList<>();
			list.add(exist);
			this.map.put(key, list);
		}
		
		list.add(value);
		
		return this;
	}

	@Override
	public Object get(Object key) {
		Object value = this.map.get(key);
		if (value == null && this.handler != null) {
			value = this.handler.onMissing((String)key);
		}
		return value;
	}
	
	public <T> T get(Object key, Class<T> clazz) throws MTypeCastException {
		Object o = this.get(key);
		T rst = null;
		if (this.handler == null) {
			rst = Macaw.cast(o, clazz);
		} else {
			try {
				rst = Macaw.cast(o, clazz);
			} catch (MTypeCastException e) {
				rst = this.handler.onCanNotCast((String)key, o, clazz);
			}
		}
		return rst;
	}
	
	public <T> T get(Object key, Class<T> clazz, T defaultValue) throws MTypeCastException {
		Object o = this.get(key);
		if (o == null) {
			return defaultValue;
		}
		
		T rst = null;
		
		if (this.handler == null) {
			rst = Macaw.cast(o, clazz);
		} else {
			try {
				rst = Macaw.cast(o, clazz);
			} catch (MTypeCastException e) {
				rst = this.handler.onCanNotCast((String)key, o, clazz);
			}
		}
		
		if (rst == null) {
			return defaultValue;
		}
		
		return rst;
	}

	@Override
	public void clear() {
		this.map.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.map.containsValue(value);
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return this.map.entrySet();
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return this.map.keySet();
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		this.map.putAll(m);
	}

	@Override
	public Object remove(Object key) {
		return this.map.remove(key);
	}

	@Override
	public int size() {
		return this.map.size();
	}

	@Override
	public Collection<Object> values() {
		return this.map.values();
	}
	
	public String getKvString() {
		if (this.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Entry<String, Object> entry : this.entrySet()) {
			if (sb.length() != 0) {
				sb.append("&");
			}
			sb.append(entry.getKey());
			if (entry.getValue() != null) {
				try {
					sb.append("=").append(Macaw.cast(entry.getValue(), String.class));
				} catch (MTypeCastException e) {
					
				}
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 
	 * @author MilesLiu
	 *
	 * 2020-06-07 10:40
	 */
	public static interface Handler {
		/**
		 * 当指定的Key对应的Value不存在时触发
		 * @param key
		 * @return 返回值作为get的返回值
		 */
		public Object onMissing(String key);
		
		/**
		 * 当key对应的Value存在，但无法转换为目标类型时触发
		 * @param key
		 * @param value 当前值
		 * @param clazz 目标类型
		 * @return 返回值作为get的返回值
		 */
		public <T> T onCanNotCast(String key, Object value, Class<T> clazz);
	}
}
