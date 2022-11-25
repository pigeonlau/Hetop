package cn.edu.nwpu.rj416.motp.serializer.motp.loader.loader;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.loader.MotpLoader;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.motp.serializer.motp.util.MotpProcesserMapping;
import cn.edu.nwpu.rj416.type.Macaw;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.util.MStringObjectMap;
import cn.edu.nwpu.rj416.util.exception.runtime.MInvalidValueException;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MVLInt;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class MotpDataLoader {
    public static Object readData(MotpLoader loader, MByteBuffer dataBuffer) {
        Object value = null;
        byte motpType = dataBuffer.readByte();

        if (motpType == MotpType.VOID) {
            return null;
        }

        MotpTypeProcesser fastProcesser = null;
        fastProcesser = MotpProcesserMapping.getProcesser(motpType);
        if (fastProcesser != null) {
            value = fastProcesser.read(dataBuffer);
        } else {
            switch (motpType) {
                case MotpType.ENUM:
                    value = EnumLoader.readEnumData(loader, dataBuffer, String.class);
                    break;
                case MotpType.OBJECT:
                    value = ObjectLoader.readObjectData(
                            loader,
                            dataBuffer,
                            MStringObjectMap.class);
                    break;
                case MotpType.LIST:
                    value = ListLoader.readListData(loader, dataBuffer, ArrayList.class);
                    break;
                case MotpType.MAP:
                    value = MapLoader.readMapData(loader, dataBuffer, HashMap.class);
                    break;
                case MotpType.SET:
                    value = SetLoader.readSetData(loader, dataBuffer, HashSet.class);
                    break;
                case MotpType.FILE:
                    value = FileLoader.readFileData(loader, dataBuffer);
                    break;
                default:
                    MotpDataLoader.readDataError("错误的Motp类型:%d", motpType);
            }
        }

        return value;
    }

    public static Object readData(MotpLoader loader, MByteBuffer dataBuffer, Type destType) {
        Object value = null;
        byte motpType = dataBuffer.readByte();

        if (motpType == MotpType.VOID) {
            return null;
        }

        if (destType == null) {
            return readData(loader, dataBuffer);
        }

        MotpTypeProcesser fastProcesser = MotpProcesserMapping.getProcesser(destType, motpType);
        if (fastProcesser != null) {
            return fastProcesser.read(dataBuffer);
        }

        fastProcesser = MotpProcesserMapping.getProcesser(motpType);
        if (fastProcesser != null) {
            value = fastProcesser.read(dataBuffer);

            if (Objects.equals(value.getClass(), destType)) {
                return value;
            }
        } else {
            switch (motpType) {
                case MotpType.ENUM:
                    value = EnumLoader.readEnumData(loader, dataBuffer, destType);
                    break;
                case MotpType.OBJECT:
                    return ObjectLoader.readObjectData(
                            loader,
                            dataBuffer, destType);
                case MotpType.LIST:
                    return ListLoader.readListData(loader, dataBuffer, destType);
                case MotpType.MAP:
                    return MapLoader.readMapData(loader, dataBuffer, destType);
                case MotpType.SET:
                    return SetLoader.readSetData(loader, dataBuffer, destType);
                case MotpType.FILE:
                    if (destType != File.class) {
                        readDataError("文件不能解析成%s类型", destType);
                    }
                    return FileLoader.readFileData(loader, dataBuffer);
                default:
                    MotpDataLoader.readDataError("错误的Motp类型:%d", motpType);
            }
        }


        try {
            return Macaw.cast(value, destType);
        } catch (MTypeCastException e) {
            return null;
        }
    }

    public static Object skipData(
            MotpLoader loader, MByteBuffer dataBuffer) {
        Object value = null;
        byte type = dataBuffer.readByte();
        switch (type) {
            case MotpType.VOID:
                break;
            case MotpType.INT8:
                dataBuffer.skip(1);
                break;
            case MotpType.INT16:
                dataBuffer.skip(2);
                break;
            case MotpType.INT32:
                dataBuffer.skip(4);
                break;
            case MotpType.INT64:
                dataBuffer.skip(8);
                break;
            case MotpType.FLOAT:
                dataBuffer.skip(4);
                break;
            case MotpType.DOUBLE:
                dataBuffer.skip(8);
                break;
            case MotpType.CHAR:
                dataBuffer.skip(2);
                break;
            case MotpType.INT_VL:
                dataBuffer.readMVLInt();
                break;
            case MotpType.STRING:
                MVLInt strLen = dataBuffer.readMVLInt();
                dataBuffer.skip(strLen.castToInteger());
                break;
            case MotpType.ENUM:
                dataBuffer.readMVLInt();
                dataBuffer.readMVLInt();
                break;
            case MotpType.BYTE_ARR:
                MVLInt bytesLen = dataBuffer.readMVLInt();
                dataBuffer.skip(bytesLen.castToInteger());
                break;
            case MotpType.OBJECT:
                dataBuffer.readMVLInt();
                MVLInt objDataLen = dataBuffer.readMVLInt();
                dataBuffer.skip(objDataLen.castToInteger());
                break;
            case MotpType.LIST:
                int listSize = dataBuffer.readMVLInt().castToInteger();
                ListLoader.skipListData(loader, dataBuffer, listSize);
                break;
            case MotpType.MAP:
                int mapSize = dataBuffer.readMVLInt().castToInteger();
                MapLoader.skipMapData(loader, dataBuffer, mapSize);
                break;
            case MotpType.SET:
                int setSize = dataBuffer.readMVLInt().castToInteger();
                SetLoader.skipSetData(loader, dataBuffer, setSize);
                break;
            case MotpType.FILE:
                FileLoader.skipFileData(loader, dataBuffer);
                break;
            default:
                MotpDataLoader.readDataError("错误的数据类型:%d", type);
        }

        return value;
    }


    public static void readDataError(String formatStr, Object... args) {
        throw new MInvalidValueException(formatStr, args);
    }

}
