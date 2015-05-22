package cz.cvut.fel.wa2.services;

import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Petikk on 16. 5. 2015.
 */
public class Mapper {
    DozerBeanMapper mapper=new DozerBeanMapper(Collections.singletonList("DozerBeanMapper.xml"));

    <T> T mapObject(Object object,Class<T> c){
        return mapper.map(object,c);
    }

    <T> List<T> mapList(List<?> objects, Class<T> c) {
        ArrayList<T> list = new ArrayList<T>(objects.size());
        for (Object object : objects) {
            list.add(mapObject(object, c));
        }
        return list;
    }
}
