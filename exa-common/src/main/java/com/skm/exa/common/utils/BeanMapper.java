package com.skm.exa.common.utils;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import ma.glasnost.orika.metadata.Type;
import ma.glasnost.orika.metadata.TypeFactory;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 对象属性值拷贝工具
 *
 * @author dhc
 * 2019-03-06 10:42
 */
public class BeanMapper {
    private static final MapperFactory MAPPER_FACTORY;
    private static final MapperFacade DEFAULT_MAPPER;

    static {
        MAPPER_FACTORY = new DefaultMapperFactory.Builder().build();
        DEFAULT_MAPPER = MAPPER_FACTORY.getMapperFacade();
    }

    /**
     * 简单的复制出新类型对象
     *
     * @param source    被复制的源对象
     * @param distClass 目标对象类型
     * @param <S>       源对象类型
     * @param <D>       目标对象类型
     * @return 复制后的对象
     */
    public static <S, D> D map(S source, Class<D> distClass) {
        return map(source, distClass, () -> DEFAULT_MAPPER);
    }

    /**
     * 简单的复制出新类型对象
     *
     * @param source    被复制的对象
     * @param distClass 目标对象类型
     * @param mapper    复制支持工具
     * @param <S>       源对象类型
     * @param <D>       目标对象类型
     * @return 复制后的对象
     */
    public static <S, D> D map(S source, Class<D> distClass, Supplier<MapperFacade> mapper) {
        return mapper.get().map(source, distClass);
    }

    /**
     * 构建一个具有Class属性对应的 MapperFacade
     *
     * @param sourceClass 转入类型
     * @param distClass   转出类型
     * @param fieldMap    转入转出属性对照 Map
     * @param <S>         转入类泛型
     * @param <D>         转出类泛型
     * @return 构建后的 MapperFacade
     */
    private static <S, D> MapperFacade buildClassMapperFacade(Class<S> sourceClass, Class<D> distClass, Map<String, String> fieldMap) {
        MapperFactory factory = new DefaultMapperFactory.Builder().build();
        ClassMapBuilder<S, D> classMapBuilder = factory.classMap(sourceClass, distClass);
        for (String key : fieldMap.keySet()) {
            classMapBuilder.field(key, fieldMap.get(key));
        }
        classMapBuilder.byDefault().register();
        return factory.getMapperFacade();
    }

    /**
     * 根据字段映射转换对象
     *
     * @param source    转入对象
     * @param distClass 转出类型
     * @param fieldMap  字段映射
     * @param <S>       转入类泛型
     * @param <D>       转出类泛型
     * @return 转换后的对象
     */
    public static <S, D> D map(S source, Class<D> distClass, Map<String, String> fieldMap) {
        return buildClassMapperFacade(source.getClass(), distClass, fieldMap).map(source, distClass);
    }


    /**
     * 根据字段映射转换对象集合
     *
     * @param sourceList  转入对象集合
     * @param sourceClass 转入对象类型
     * @param distClass   转出类型
     * @param fieldMap    字段映射
     * @param <S>         转入类泛型
     * @param <D>         转出类泛型
     * @return 转换后的对象集合
     */
    public static <S, D> List<D> mapList(Iterable<S> sourceList, Class<S> sourceClass, Class<D> distClass, Map<String, String> fieldMap) {
        return buildClassMapperFacade(sourceClass, distClass, fieldMap).mapAsList(sourceList, TypeFactory.valueOf(sourceClass), TypeFactory.valueOf(distClass));
    }

    /**
     * 极致性能的复制出新类型对象.
     * <p>
     * 预先通过BeanMapper.getType() 静态获取并缓存Type类型，在此处传入
     */
    public static <S, D> D map(S source, Type<S> sourceType, Type<D> distType) {
        return DEFAULT_MAPPER.map(source, sourceType, distType);
    }

    /**
     * 简单的复制出新对象列表到ArrayList
     * <p>
     * 不建议使用mapper.mapAsList(Iterable<S>,Class<D>)接口, sourceClass需要反射，实在有点慢
     */
    public static <S, D> List<D> mapList(Iterable<S> sourceList, Class<S> sourceClass, Class<D> distClass) {
        return DEFAULT_MAPPER.mapAsList(sourceList, TypeFactory.valueOf(sourceClass), TypeFactory.valueOf(distClass));
    }

    /**
     * 极致性能的复制出新类型对象到ArrayList.
     * <p>
     * 预先通过BeanMapper.getType() 静态获取并缓存Type类型，在此处传入
     */
    public static <S, D> List<D> mapList(Iterable<S> sourceList, Type<S> sourceType, Type<D> destinationType) {
        return DEFAULT_MAPPER.mapAsList(sourceList, sourceType, destinationType);
    }

    /**
     * 简单复制出新对象列表到数组
     * <p>
     * 通过source.getComponentType() 获得源Class
     */
    public static <S, D> D[] mapArray(final D[] destination, final S[] source, final Class<D> destinationClass) {
        return DEFAULT_MAPPER.mapAsArray(destination, source, destinationClass);
    }

    /**
     * 极致性能的复制出新类型对象到数组
     * <p>
     * 预先通过BeanMapper.getType() 静态获取并缓存Type类型，在此处传入
     */
    public static <S, D> D[] mapArray(D[] destination, S[] source, Type<S> sourceType, Type<D> destinationType) {
        return DEFAULT_MAPPER.mapAsArray(destination, source, sourceType, destinationType);
    }

    /**
     * 预先获取orika转换所需要的Type，避免每次转换.
     */
    public static <E> Type<E> getType(final Class<E> rawType) {
        return TypeFactory.valueOf(rawType);
    }

    public static MapperFactory getFactory() {
        return MAPPER_FACTORY;
    }

    /**
     * 将一个对象的属性值拷贝到目标对象中
     *
     * @param src          源对象
     * @param target       目标对象
     * @param ignoreFields 需要忽略的属性
     */
    public static void copyProperties(Object src, Object target, String... ignoreFields) {
        BeanUtils.copyProperties(src, target, ignoreFields);
    }
}
