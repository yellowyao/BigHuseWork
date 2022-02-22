function initMap() {
	//定义地图中心点坐标
	var center = new TMap.LatLng(33, 110)
	//定义map变量，调用 TMap.Map() 构造函数创建地图
	var map = new TMap.Map(document.getElementById('container'), {
		center: center, //设置地图中心点坐标
		zoom: 5, //设置地图缩放级别
		viewMode: '2D',
		baseMap: { //底图设置（参数为：VectorBaseMap对象）
			type: 'vector', //类型：失量底图
			features: ['base', 'building2d']
			//仅渲染：道路及底面(base) + 2d建筑物(building2d)，以达到隐藏文字的效果
		}
	});

	//创建并初始化MultiMarker
	var markerLayer = new TMap.MultiMarker({
		map: map, //指定地图容器
		//样式定义
		styles: {
			//创建一个styleId为"myStyle"的样式（styles的子属性名即为styleId）
			"myStyle": new TMap.MarkerStyle({
				"width": 25, // 点标记样式宽度（像素）
				"height": 35, // 点标记样式高度（像素）
				"src": './img/定位_地点_地址_爱给网_aigei_com.png', //图片路径
				//焦点在图片中的像素位置，一般大头针类似形式的图片以针尖位置做为焦点，圆形点以圆心位置为焦点
				"anchor": {
					x: 16,
					y: 32
				}
			})
		},

		//点标记数据数组
		geometries: [{
			"id": "1", //点标记唯一标识，后续如果有删除、修改位置等操作，都需要此id
			"styleId": 'marker', //指定样式id
			"position": new TMap.LatLng(33, 116.357503), //点标记坐标位置
			"properties": { //自定义属性
				"title": "markerdsad1"
			}
		}, { //第二个点标记
			"id": "2",
			"styleId": 'marker',
			"position": new TMap.LatLng(39.994104, 116.287503),
			'content': '腾讯北京总部'
		}]
	});
}

function TestFirst(first) {
	var reg = new RegExp(
		/^[\-\+]?(0?\d{1,2}|0?\d{1,2}\.\d{1,15}|1[0-7]?\d{1}|1[0-7]?\d{1}\.\d{1,15}|180|180\.0{1,15})$/)
	var result = reg.test(first);
	return result
}

function TestSecond(Second) {
	var reg = new RegExp(/^[\-\+]?([0-8]?\d{1}|[0-8]?\d{1}\.\d{1,15}|90|90\.0{1,15})$/)
	var result = reg.test(Second);
	return result
}
new Vue({
	el: '#app',
	data: {
		city: {
			name: "",
			first: "",
			second: ""
		},
		index: 1,
		distance: "",
		map: {
			center: "",
			map: "",
			markerLayer: ""
		},
		circle: "",
		flag: false

	},
	methods: {
		initMap() {

			//定义地图中心点坐标
			this.map.center = new TMap.LatLng(33, 110)
			//定义map变量，调用 TMap.Map() 构造函数创建地图
			this.map.map = new TMap.Map(document.getElementById('container'), {
				center: this.map.center, //设置地图中心点坐标
				zoom: 5, //设置地图缩放级别
				viewMode: '2D',
				baseMap: { //底图设置（参数为：VectorBaseMap对象）
					type: 'vector', //类型：失量底图
					features: ['base', 'building2d']
					//仅渲染：道路及底面(base) + 2d建筑物(building2d)，以达到隐藏文字的效果
				}
			});
			axios.get("/map/init").then((resp) => {
				console.log(resp);
				//创建并初始化MultiMarker
				this.map.markerLayer = new TMap.MultiMarker({
					map: this.map.map, //指定地图容器
					styles: {
						//创建一个styleId为"myStyle"的样式（styles的子属性名即为styleId）
						"myStyle": new TMap.MarkerStyle({
							"width": 35, // 点标记样式宽度（像素）
							"height": 55, // 点标记样式高度（像素）
							"src": '../img/定位_地点_地址_爱给网_aigei_com.png', //图片路径
							//焦点在图片中的像素位置，一般大头针类似形式的图片以针尖位置做为焦点，圆形点以圆心位置为焦点
							"anchor": {
								x: 16,
								y: 32
							}
						})
					},
				});
				this.map.markerLayer.setGeometries([])
				for (index in resp.data.data) {
					var obj = resp.data.data[index]
					this.map.markerLayer.add([{
						"id": obj.name,
						"styleId": 'myStyle',
						"position": new TMap.LatLng(parseInt(obj.second), parseInt(obj
							.first)),
						'content': obj.name
					}])
				}
			})
		},
		search1() {
			axios.post("/map/search", this.city).then((resp) => {
				alert(resp.data.message)
				this.map.map.setCenter(new TMap.LatLng(resp.data.data.second, resp.data.data.first));
				this.map.map.setZoom(6)
				if (this.flag) {
					this.circle.setMap(null)
					this.flag = false
				}
			})
		},
		add1() {
			console.log(this.city);
			if (!TestFirst(this.city.first)) {
				alert("经度，输入不合法！")
				return
			}
			if (!TestSecond(this.city.second)) {
				alert("纬度，输入不合法！")
				return
			}
			axios.post("/map/add", this.city).then((resp) => {
				alert(resp.data.message)
				this.map.markerLayer.add([{
					"id": this.city.name,
					"styleId": 'marker',
					"position": new TMap.LatLng(parseInt(this.city.second), parseInt(this
						.city.first)),
					'content': this.city.name
				}])
				this.map.map.setCenter(new TMap.LatLng(resp.data.data.second, resp.data.data.first));
				if (this.flag) {
					this.circle.setMap(null)
					this.flag = false
				}
			})
		},
		searchNear1() {

			axios.post("/map/search", this.city).then((resp) => {
				if(resp.data.message=="该城市不存在"){
					alert(resp.data.message)
					return
				}
				this.map.map.setCenter(new TMap.LatLng(resp.data.data.second, resp.data.data.first));
				this.map.map.setZoom(6)
				var map = this.map.map
				//圆
				if (this.flag) {
					this.circle.setMap(null)
					this.flag=false
				}
				this.circle = new TMap.MultiCircle({
					map,
					styles: { // 设置圆形样式
						'circle': new TMap.CircleStyle({
							'color': 'rgba(41,91,255,0.16)',
							'showBorder': true,
							'borderColor': 'rgba(41,91,255,1)',
							'borderWidth': 2,
						}),
					},
					geometries: [{
						styleId: 'circle',
						center: new TMap.LatLng(resp.data.data.second, resp.data.data
							.first), //圆形中心点坐标 
						radius: parseInt(this.distance) * 1000, //半径（单位：米）
					}],
				});
				this.map.markerLayer.setGeometries([])
				this.map.markerLayer.add([{
					"id": resp.data.data.name,
					"styleId": 'myStyle',
					"position": new TMap.LatLng(parseInt(resp.data.data.second),
						parseInt(resp.data.data
							.first)),
					'content': resp.data.data.name
				}])

				

				axios.post("/map/searchNear?distance=" + this.distance, this.city).then((resp) => {
					console.log(resp);
					this.map.markerLayer.setGeometries([])
					for (index in resp.data.data.listElem) {
						var obj = resp.data.data.listElem[index]
						if (obj == null) {
							break
						}
						this.map.markerLayer.add([{
							"id": obj.name,
							"styleId": 'myStyle',
							"position": new TMap.LatLng(parseInt(obj.second),
								parseInt(obj
									.first)),
							'content': obj.name
						}])
					}
					this.flag = true
					alert(resp.data.message)

				})

			})
			/* 
			axios.post("/map/search", this.city).then((resp) => {
				this.map.map.setCenter(new TMap.LatLng(resp.data.data.first, resp.data.data.second));
				this.map.map.setZoom(6)
				var map = this.map.map
				this.circle = new TMap.MultiCircle({
					map,
					styles: { // 设置圆形样式
						'circle': new TMap.CircleStyle({
							'color': 'rgba(41,91,255,0.16)',
							'showBorder': true,
							'borderColor': 'rgba(41,91,255,1)',
							'borderWidth': 2,
						}),
					},
					geometries: [{
						styleId: 'circle',
						center: new TMap.LatLng(resp.data.data.first, resp.data.data.second), //圆形中心点坐标 
						radius: parseInt(this.distance) * 1000, //半径（单位：米）
					}],
				});
				axios.post("/map/searchNear?distance=" + this.distance, this.city).then((resp) => {
					alert(resp.data.message)
					console.log(resp);
					this.map.markerLayer.setGeometries([])
					for (index in resp.data.data.listElem) {
						var obj = resp.data.data.listElem[index]
						if (obj == null) {
							break
						}
						this.map.markerLayer.add([{
							"id": obj.name,
							"styleId": 'myStyle',
							"position": new TMap.LatLng(parseInt(obj.first),
								parseInt(obj
									.second)),
							'content': obj.name
						}])
					}
				})
			}) */

		},
		delete1() {
			axios.post("/map/delete?name=" + this.city.name).then((resp) => {
				alert(resp.data.message)
				this.map.markerLayer.remove([this.city.name])
				if (this.flag) {
					this.circle.setMap(null)
					this.flag = false
				}
			})
		},
		initbutton() {
			axios.post("/map/initButton").then((resp) => {
				this.map.markerLayer.setGeometries([])
				for (index in resp.data.data) {
					var obj = resp.data.data[index]
					this.map.markerLayer.add([{
						"id": obj.name,
						"styleId": 'myStyle',
						"position": new TMap.LatLng(parseInt(obj.second), parseInt(obj
							.first)),
						'content': obj.name
					}])
				}
				if (this.flag) {
					this.circle.setMap(null)
					this.flag = false
				}
			})
		}

	},
	mounted: function() {
		this.initMap()
	}
});
