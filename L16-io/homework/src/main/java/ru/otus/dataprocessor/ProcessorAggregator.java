package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        Map<String, Double> res = data.stream()
                .collect(
                        Collectors.groupingBy(
                                Measurement::getName,
                                TreeMap::new,
                                Collectors.summingDouble(Measurement::getValue)
                        )
                );
        System.out.println(res);
        //var a = res.entrySet().stream()
        //        .sorted((e1,e2) -> e1.getKey().compareTo(e2.getKey()))
        //        .collect(Collectors.toList());
        //System.out.println("res="+a);
        return res;
    }
}
