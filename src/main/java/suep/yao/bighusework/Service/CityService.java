package suep.yao.bighusework.Service;

import suep.yao.bighusework.entity.City;
import suep.yao.bighusework.vo.Result;

public interface CityService {


    public Result insertCity(City city);

    public Result findCityByName(String cityName);

    public Result deleteCityByName(String cityName);

    public Result findCityByNameAndDistance(String cityName, String distance);

    public Result init();

    void add(City city) throws Exception;

    Result initButton();

}
