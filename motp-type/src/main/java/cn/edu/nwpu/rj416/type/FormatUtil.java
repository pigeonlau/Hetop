package cn.edu.nwpu.rj416.type;


import cn.edu.nwpu.rj416.util.reflect.ClassUtil;
import cn.edu.nwpu.rj416.util.text.format.MFmtLine;
import cn.edu.nwpu.rj416.util.text.format.MFmtToken;
import cn.edu.nwpu.rj416.util.text.format.MFormatable;
import cn.edu.nwpu.rj416.util.types.CollectionUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class FormatUtil {
	public static final long TEXT_ID = 0L;
	public static final long TYPE_TOKEN_ID = 1L;
	public static final long TYPE_NAME_ID = 2L;
	public static final long COLUMN_NAME_ID = 3L;
	
	public static List<MFmtLine> format(Object obj, int level) {
		List<MFmtLine> rst = new ArrayList<>();
		FormatUtil.appendLines(rst, obj, level);
		return rst;
	}
	
	private static void appendLines(List<MFmtLine> lines, Object obj, int level) {
		if (obj == null) {
			FormatUtil.appendNull(lines, level);
			return;
		}
		
		Class<?> clazz = obj.getClass();
		
		if (clazz == String.class) {
			String str = (String)obj;
			MFmtLine line = new MFmtLine(level);
			line.appendToken(TEXT_ID, "[");
			line.appendToken(TYPE_NAME_ID, clazz.getName());
			line.appendToken(TEXT_ID, "]");
			if (str.length() > 80) {
				line.appendToken(str.substring(0, 80));
			} else {
				line.appendToken(str);
			}
			lines.add(line);
			return;
		}
		if (clazz.isPrimitive()) {
			MFmtLine line = new MFmtLine(level);
			line.appendToken(TEXT_ID, "[");
			line.appendToken(TYPE_NAME_ID, clazz.getName());
			line.appendToken(TEXT_ID, "]");
			String text = String.valueOf(obj);
			line.appendToken(text);
			lines.add(line);
			return;
		}
		
		if (clazz == Boolean.class 
				|| clazz == Byte.class
				|| clazz == Short.class
				|| clazz == Integer.class
				|| clazz == Long.class
				|| clazz == Float.class
				|| clazz == Double.class
				|| clazz == Character.class
				|| clazz == BigDecimal.class
				|| clazz == BigInteger.class
				|| clazz == Date.class) {
			MFmtLine line = new MFmtLine(level);
			line.appendToken(TEXT_ID, "[");
			line.appendToken(TYPE_NAME_ID, clazz.getName());
			line.appendToken(TEXT_ID, "]");
			String text = TypeCaster.cast(obj, String.class);
			line.appendToken(text);
			lines.add(line);
			return;
		}
		
		if (obj instanceof MFormatable) {
			MFormatable fmtObj = (MFormatable)obj;
//			MFmtLine line = new MFmtLine(level);
//			line.appendToken(TEXT_ID, "[");
//			line.appendToken(TYPE_TOKEN_ID, "MFormatable");
//			line.appendToken(TEXT_ID, ":");
//			line.appendToken(TYPE_NAME_ID, obj.getClass().getName());
//			line.appendToken(TEXT_ID, "]");
//			lines.add(line);
			
			List<MFmtLine> detailLines = fmtObj.format(level);
			lines.addAll(detailLines);
			return;
		}
		
		if (clazz == Class.class) {
			Class<?> classObj = (Class<?>)obj;
			MFmtLine line = new MFmtLine(level);
			line.appendToken(TEXT_ID, "[");
			line.appendToken(TYPE_TOKEN_ID, String.format("Class(%s)", classObj.getName()));
			line.appendToken(TEXT_ID, "]");
			lines.add(line);
			return;
		}
		
		if (clazz.isEnum()) {
			Enum<?> enumObj = (Enum<?>)obj;
			FormatUtil.appendEnum(lines, enumObj, level);
			return;
		}
		
		if (clazz.isArray()) {
			FormatUtil.appendArray(lines, obj, level);
			return;
		}
		
		if (List.class.isAssignableFrom(clazz)) {
			FormatUtil.appendList(lines, obj, level);
			return;
		}
		
		if (Set.class.isAssignableFrom(clazz)) {
			FormatUtil.appendSet(lines, obj, level);
			return;
		}
		
		if (Map.class.isAssignableFrom(clazz)) {
			FormatUtil.appendMap(lines, obj, level);
			return;
		}
		
		FormatUtil.appendNormalObject(lines, obj, level);
	}
	
	private static void appendNull(List<MFmtLine> lines, int level) {
		MFmtLine line = new MFmtLine(level);
		line.appendToken(TEXT_ID, "[null]");
		lines.add(line);
	}
	
	private static void appendEnum(List<MFmtLine> lines, Enum<?> enumObj, int level) {
		MFmtLine line = new MFmtLine(level);
		line.appendToken(TEXT_ID, "[");
		line.appendToken(TYPE_TOKEN_ID, "Enum");
		line.appendToken(TEXT_ID, ":");
		line.appendToken(TYPE_NAME_ID, enumObj.getClass().getName());
		line.appendToken(TEXT_ID, "]");
		line.appendToken(TEXT_ID, enumObj.name());
		line.appendToken(TEXT_ID, String.format("(%d)", enumObj.ordinal()));
		lines.add(line);
	}

	private static void appendArray(List<MFmtLine> lines, Object arrObj, int level) {
		if (arrObj == null) {
			FormatUtil.appendNull(lines, level);
			return;
		}
		
		int len = Array.getLength(arrObj);
		
		MFmtLine line = new MFmtLine(level);
		line.appendToken(TEXT_ID, "[");
		line.appendToken(TYPE_TOKEN_ID, 
				String.format("%s[%d]", 
						arrObj.getClass().getComponentType().getName(), 
						len));
		line.appendToken(TEXT_ID, "]");
		lines.add(line);
		
		for (int i = 0; i < len; i++) {
			Object ele = Array.get(arrObj, i);
			List<MFmtLine> detail = new ArrayList<>();
			MFmtLine eleLine = new MFmtLine(level + 1);
			eleLine.appendToken(String.format("[%d]", i));
			lines.add(eleLine);
			
			FormatUtil.appendLines(detail, ele, level + 2);
			if (detail.isEmpty()) {
				continue;
			}
			/*
			 * 将第一行合并至eleLine
			 */
			for (MFmtToken tk : detail.get(0).getTokenList()) {
				eleLine.appendToken(tk.getId(), tk.getText());
			}
			
			/*
			 * 合并详情
			 */
			for (int k = 1; k < detail.size(); k++) {
				lines.add(detail.get(k));
			}
		}
		
		return;
	}
	

	private static void appendList(List<MFmtLine> lines, Object listObj, int level) {
		if (listObj == null) {
			FormatUtil.appendNull(lines, level);
			return;
		}
		List<?> list = (List<?>)listObj;
		
		MFmtLine line = new MFmtLine(level);
		line.appendToken(TEXT_ID, "[");
		line.appendToken(TYPE_TOKEN_ID, 
				String.format("List(%s)<>(%d)", list.getClass().getName(), list.size()));
		line.appendToken(TEXT_ID, "]");
		lines.add(line);
		
		for (int i = 0; i < list.size(); i++) {
			Object ele = list.get(i);
			List<MFmtLine> detail = new ArrayList<>();
			MFmtLine eleLine = new MFmtLine(level + 1);
			eleLine.appendToken(String.format("[%d]", i));
			lines.add(eleLine);
			
			FormatUtil.appendLines(detail, ele, level + 2);
			if (detail.isEmpty()) {
				continue;
			}
			/*
			 * 将第一行合并至eleLine
			 */
			for (MFmtToken tk : detail.get(0).getTokenList()) {
				eleLine.appendToken(tk.getId(), tk.getText());
			}
			
			/*
			 * 合并详情
			 */
			for (int k = 1; k < detail.size(); k++) {
				lines.add(detail.get(k));
			}
		}
		
		return;
	}

	private static void appendSet(List<MFmtLine> lines, Object setObj, int level) {
		if (setObj == null) {
			FormatUtil.appendNull(lines, level);
			return;
		}
		Set<?> set = (Set<?>)setObj;
		
		MFmtLine line = new MFmtLine(level);
		line.appendToken(TEXT_ID, "[");
		line.appendToken(TYPE_TOKEN_ID, 
				String.format("Set(%s)<>(%d)", set.getClass().getName(), set.size()));
		line.appendToken(TEXT_ID, "]");
		lines.add(line);
		
		for (Object ele : set) {
			FormatUtil.appendLines(lines, ele, level + 1);
		}
		
		return;
	}


	private static void appendMap(List<MFmtLine> lines, Object mapObj, int level) {
		if (mapObj == null) {
			FormatUtil.appendNull(lines, level);
			return;
		}
		Map<?, ?> map = (Map<?,?>)mapObj;
		
		MFmtLine line = new MFmtLine(level);
		line.appendToken(TEXT_ID, "[");
		line.appendToken(TYPE_TOKEN_ID, 
				String.format("Map(%s)<>(%d)", map.getClass().getName(), map.size()));
		line.appendToken(TEXT_ID, "]");
		lines.add(line);
		
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			List<MFmtLine> keyLines = new ArrayList<>();
			FormatUtil.appendLines(keyLines, entry.getKey(), level + 1);
			if (keyLines.isEmpty()) {
				MFmtLine kl = new MFmtLine(level + 1);
				kl.appendToken(TEXT_ID, "[key]");
				lines.add(kl);
			} else {
				keyLines.get(0).prependToken("[key]");
				lines.addAll(keyLines);
			}
			List<MFmtLine> valueLines = new ArrayList<>();
			FormatUtil.appendLines(valueLines, entry.getValue(), level + 1);
			if (valueLines.isEmpty()) {
				MFmtLine kl = new MFmtLine(level + 1);
				kl.appendToken(TEXT_ID, "[value]");
				lines.add(kl);
			} else {
				valueLines.get(0).prependToken("[value]");
				lines.addAll(valueLines);
			}
		}
		
		return;
	}
	
	private static void appendNormalObject(List<MFmtLine> lines, Object obj, int level) {
		if (obj == null) {
			FormatUtil.appendNull(lines, level);
			return;
		}
		
		Class<?> clazz = obj.getClass();

		MFmtLine objectLine = new MFmtLine(level);
		objectLine.appendToken(TEXT_ID, "[");
		objectLine.appendToken(TYPE_TOKEN_ID, 
				String.format("Object(%s)", clazz.getName()));
		objectLine.appendToken(TEXT_ID, "]");
		lines.add(objectLine);
		
		List<Field> fields = ClassUtil.getEffectiveFields(clazz, Modifier.FINAL | Modifier.STATIC);
		if (fields == null) {
			return;
		}
		for (Field f : fields) {
			f.setAccessible(true);
			Object columnValue;
			try {
				columnValue = f.get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				columnValue = null;
			}
			
			if (columnValue == null) {
				MFmtLine nullLine = new MFmtLine(level + 1);
				nullLine.appendToken(String.format("%s:[null]", f.getName()));
				lines.add(nullLine);
				continue;
			}
			
			List<MFmtLine> memberLines = new ArrayList<>();
			FormatUtil.appendLines(memberLines, columnValue, level + 1);
			if (memberLines.isEmpty()) {
				continue;
			}
			memberLines.get(0).prependToken(String.format("%s:", f.getName()));
			lines.addAll(memberLines);
		}
		return;
	}
	
	public static String toString(MFmtLine line, String indentStr) {
		StringBuilder sb = new StringBuilder();
		if (line == null) {
			return "";
		}
		for (int i = 0; i < line.getIndent(); i++) {
			sb.append(indentStr);
		}
		
		for (MFmtToken t : line.getTokenList()) {
			sb.append(t.getText());
		}
		
		return sb.toString();
	}
	

	public static String toString(List<MFmtLine> lines, String indentStr, String newLineStr) {
		if (CollectionUtil.isEmpty(lines)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (MFmtLine line : lines) {
			if (line != null) {
				for (int i = 0; i < line.getIndent(); i++) {
					sb.append(indentStr);
				}
				
				for (MFmtToken t : line.getTokenList()) {
					sb.append(t.getText());
				}
			}
			sb.append(newLineStr);
		}
		
		return sb.toString();
	}
	
	public static String toString(Object o) {
		return toString(format(o, 0), "    ", System.lineSeparator());
	}
	
	public static void print(List<MFmtLine> lines, String indentStr) {
		if (CollectionUtil.isEmpty(lines)) {
			return;
		}
		for (MFmtLine line : lines) {
			System.out.println(FormatUtil.toString(line, indentStr));
		}
	}
	
	public static void print(List<MFmtLine> lines) {
		if (CollectionUtil.isEmpty(lines)) {
			return;
		}
		for (MFmtLine line : lines) {
			System.out.println(FormatUtil.toString(line, "    "));
		}
	}
	
	public static void print(MFmtLine line, String indentStr) {
		System.out.println(FormatUtil.toString(line, indentStr));
	}
	
	public static void print(MFmtLine line) {
		System.out.println(FormatUtil.toString(line, "    "));
	}
	
	public static void print(Object obj, String indentStr) {
		List<MFmtLine> lines = FormatUtil.format(obj, 0);
		FormatUtil.print(lines, indentStr);
	}
	
	public static void print(Object obj) {
		List<MFmtLine> lines = FormatUtil.format(obj, 0);
		FormatUtil.print(lines);
	}
	
	public static void print(MFormatable formatable, String indentStr) {
		FormatUtil.print(formatable.format(0), indentStr);
	}
	
	public static void print(MFormatable formatable) {
		FormatUtil.print(formatable.format(0));
	}
}
