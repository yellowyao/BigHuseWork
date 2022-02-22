package suep.yao.bighusework.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import suep.yao.bighusework.Service.CityService;
import suep.yao.bighusework.entity.City;
import suep.yao.bighusework.vo.Result;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/map")
public class MapController {
    @Resource
    private CityService cityService;

    @RequestMapping("/add")
    public Result add(@RequestBody City city) throws Exception {
        return cityService.insertCity(city);
    }

    @RequestMapping("/init")
    public Result init() {
        return cityService.init();
    }

    @RequestMapping("/search")
    public Result search(@RequestBody City city) {
        return cityService.findCityByName(city.getName());
    }

    @RequestMapping("/searchNear")
    public Result searchNear(String distance, @RequestBody City city) {
        return cityService.findCityByNameAndDistance(city.getName(), distance);
    }

    @RequestMapping("/delete")
    public Result delete(String name) {
        return cityService.deleteCityByName(name);
    }
    @RequestMapping("/initButton")
    public Result initButton(){
        return cityService.initButton();
    }
}
