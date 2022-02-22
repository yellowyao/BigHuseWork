package suep.yao.bighusework.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import suep.yao.bighusework.Dao.CityDao;
import suep.yao.bighusework.Service.CityService;
import suep.yao.bighusework.entity.City;
import suep.yao.bighusework.util.MapUtils;
import suep.yao.bighusework.vo.Result;
import suep.yao.bighusework.vo.SqLink.SqLink;
import suep.yao.bighusework.vo.Tree.BinaryTree;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    @Autowired
    private BinaryTree binaryTree;

    @Override
    public Result insertCity(City city) {
        try {
            binaryTree.balanceInsert(city);
            cityDao.insertCity(city);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            return new Result(false, "添加失败");
        }

    }

    @Override
    public Result findCityByName(String cityName) {

        try {
            City city = binaryTree.findByName(cityName);
            if(city==null){
                return new Result(false, "该城市不存在");
            }else {

                return new Result(true, "查找成功", city);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查找失败");
        }
    }

    @Override
    public Result deleteCityByName(String cityName) {
        try {

            binaryTree.deleteByName(cityName);
            cityDao.deleteByName(cityName);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    @Override
    public Result findCityByNameAndDistance(String cityName, String distance) {
        try {
            City byName = binaryTree.findByName(cityName);
            double distance1 = Double.parseDouble(distance);
            Object[] allCity = cityDao.findAllCity();
            SqLink sqLink = new SqLink(allCity.length);
            int i=0;
            for (Object city : allCity) {
                City city1 = (City) city;
                double distance2 = MapUtils.GetDistance(Double.parseDouble(byName.getFirst()), Double.parseDouble(byName.getSecond()), Double.parseDouble(city1.getFirst()), Double.parseDouble(city1.getSecond()));
                if (distance1>=distance2){
                    sqLink.insert(i,city1);
                    i++;
                }
            }
            return new Result(true, "查找成功",sqLink);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查找失败");
        }
    }

    @Override
    public Result init() {
        Object[] allCity = cityDao.findAllCity();
        binaryTree = new BinaryTree(allCity);
        System.out.println("初始化成功");
        return new Result(true, "ok", allCity);
    }
    @Override
    public void add(City city) throws Exception {
        System.out.println(city);
        binaryTree.levelRoot();
    }

    @Override
    public Result initButton() {
        Object[] allCity = cityDao.findAllCity();
        return new Result(true, "ok", allCity);
    }
}
