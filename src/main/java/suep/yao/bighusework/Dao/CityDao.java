package suep.yao.bighusework.Dao;

import suep.yao.bighusework.entity.City;

public interface CityDao {
    Object[] findAllCity();

    void insertCity(City city);

    void deleteByName(String cityName);
}
