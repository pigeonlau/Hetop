package cn.edu.nwpu.rj416.type;


import cn.edu.nwpu.rj416.type.caster.array.*;
import cn.edu.nwpu.rj416.type.caster.astype.*;
import cn.edu.nwpu.rj416.type.caster.bigInteger.MCasterBigInteger2String;
import cn.edu.nwpu.rj416.type.caster.bigdecimal.MCasterBigDecimal2String;
import cn.edu.nwpu.rj416.type.caster.date.MCasterDate2Long;
import cn.edu.nwpu.rj416.type.caster.date.MCasterDate2String;
import cn.edu.nwpu.rj416.type.caster.list.*;
import cn.edu.nwpu.rj416.type.caster.map.MCasterMap2Object;
import cn.edu.nwpu.rj416.type.caster.murl.MCasterMurl2String;
import cn.edu.nwpu.rj416.type.caster.object.*;
import cn.edu.nwpu.rj416.type.caster.primitive.*;
import cn.edu.nwpu.rj416.type.caster.string.*;

class DefaultCasters {
	//所有自定义转换器的Class对象数组集合
	public static final Class<?>[] DefaultCasterClasses = {
			MCasterArrayToList.class,
			MCasterBooleanArrayToList.class,
			MCasterByteArrayToList.class,
			MCasterCharArrayToList.class,
			MCasterDoubleArrayToList.class,
			MCasterFloatArrayToList.class,
			MCasterIntArrayToList.class,
			MCasterLongArrayToList.class,
			MCasterShortArrayToList.class,
			
			MCasterAsType2BigDecimal.class,
			MCasterAsType2Boolean.class,
			MCasterAsType2Byte.class,
			MCasterAsType2Int.class,
			MCasterAsType2String.class,
			
			MCasterBigInteger2String.class,
			
			MCasterBigDecimal2String.class,
			
			MCasterDate2Long.class,
			MCasterDate2String.class,
			
			MCasterList2BooleanArray.class,
			MCasterList2ByteArray.class,
			MCasterList2CharArray.class,
			MCasterList2DoubleArray.class,
			MCasterList2FloatArray.class,
			MCasterList2IntArray.class,
			MCasterList2LongArray.class,
			MCasterList2ObjectArray.class,
			MCasterList2ShortArray.class,
			MCasterList2StringArray.class,
			
			MCasterMap2Object.class,
			
			MCasterMurl2String.class,
			
			MCasterObject2Boolean.class,
			MCasterObject2MStringObjectMap.class,
			MCasterObject2Map.class,
			MCasterObject2Object.class,
			MCasterObject2String.class,
			
			MCasterBoolean2BigDecimal.class,
			MCasterBoolean2Byte.class,
			MCasterBoolean2Int.class,
			
			MCasterDouble2BigDecimal.class,
			MCasterDouble2Byte.class,
			MCasterDouble2Int.class,
			
			MCasterFloat2BigDecimal.class,
			MCasterFloat2Byte.class,
			MCasterFloat2Int.class,
			
			MCasterInteger2BigDecimal.class,
			MCasterInteger2BigInteger.class,
			MCasterInteger2Byte.class,
			MCasterInteger2Double.class,
			MCasterInteger2Float.class,
			MCasterInteger2Long.class,
			MCasterInteger2Short.class,
			
			MCasterLong2BigDecimal.class,
			MCasterLong2Byte.class,
			MCasterLong2Date.class,
			MCasterLong2Int.class,
			
			MCasterShort2Byte.class,
			MCasterShort2Int.class,
			
			MCasterString2BigDecimal.class,
			MCasterString2BigInteger.class,
			MCasterString2Boolean.class,
			MCasterString2Byte.class,
			MCasterString2Date.class,
			MCasterString2Double.class,
			MCasterString2Enum.class,
			MCasterString2Float.class,
			MCasterString2Int.class,
			MCasterString2Long.class,
			MCasterString2Murl.class,
			MCasterString2Short.class,
		};
}
