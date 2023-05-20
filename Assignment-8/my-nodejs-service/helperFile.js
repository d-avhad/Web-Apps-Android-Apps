import fetch from 'node-fetch' ;
import async from 'express-async-await' ;
import url from 'url' ;
import https from 'https' ;
import axios from 'axios';


const yelpAPIkey = 'your key';

const header_str='Bearer '+yelpAPIkey 


async function getGeolocation(loc,prev_res, data) {
	
	var addr= encodeURIComponent(loc)		
	let lat=0.0;
	let lng=0.0 ;
		
		
	await axios.get("https://maps.googleapis.com/maps/api/geocode/json?address="+addr+"&key=your key", {
			  headers: {
				'Access-Control-Allow-Origin':'*' 
			  }
			}).then( function (resp) {
				
				console.log("res data is *********",resp.data.results.length, resp.data)
				if(resp.data.results.length==0) {
				
					prev_res.send();
					
				}
				else {
					 console.log("geolocation len",(resp.data).length)
			   console.log("geolocation response", JSON.parse(JSON.stringify(resp.data)).results[0].geometry.location.lat);	
			   lat= JSON.parse(JSON.stringify(resp.data)).results[0].geometry.location.lat ;
			   lng= JSON.parse(JSON.stringify(resp.data)).results[0].geometry.location.lng;
			   callYelpAPI(prev_res, data, lat, lng);
				}
			   
			  
				
			  }) ;
	  	
}


// function to get 10 yelp business search results.

async function callYelpAPI(prev_res, data, lat , lng) {
	
	let ans_json=[];
	//let header_str='Bearer '+yelpAPIkey ;
	let keyword=data.keyword;
	let radius=Math.floor(data.distance*1609.34) ;
	let category=data.category ;
	let latitude=0.0;
	let longitude=0.0;
	
	if(category=="Arts & Entertainment")
		category="arts" ;
	
	else if(category=="Food")
		category="food" ;
	
	else if (category=="Health & Medical")
		category="health" ;
	
	else if(category=="Hotels & Travel")
		category="hotelstravel"
	
	else if (category=="Professional Services")
		category="professional"	
	
	else if (data.category=="Default")
		category="All"		
	
	
	
	if(lat==""  &&  lng== "") {
		
		latitude=data.latitude;
		longitude=data.longitude;		
		
	}
	
	else 
	{
		latitude=lat;
		longitude=lng;
			
	}
	
	console.log("category",category)
	console.log("keyword",keyword)
	console.log("radius",radius)
	console.log("lat",latitude)
	console.log("lng",longitude)
//	console.log("category",category)
	
	
	
 const ans= await axios.get('https://api.yelp.com/v3/businesses/search', {
	  headers: {
		'Authorization':header_str,'Access-Control-Allow-Origin':'*'
	  },
	  params: {
		  
		  "term":keyword, "latitude":latitude, "longitude":longitude, "categories":category, "radius":radius , "limit":10 
	  }
  
	  }).then( function (response) {
		  
		   //console.log("response",response.data);
		   
		   let  res_hold=response.data.businesses 
		   
		   for (let i=0; i< (response.data. businesses).length ; i++ ) {
			  // console.log("i is", i);
			   
			   let int_val=Math.floor(res_hold[i].distance/1609.34)
			   ans_json[i]={"id":res_hold[i].id, 
							"img_url":res_hold[i].image_url,
							"bus_name":res_hold[i].name ,
							"rating":res_hold[i].rating,
							"dist": int_val
							
							} 
			   
			   
		   }
		   
		  // console.log("res_hold",(ans_json[0]));
		  prev_res.send(ans_json)
			
			
		  }) ;



//prev_res.send({"st":"ok"});
 

} //callYelpAPI function ends.




async function getCardDetails(res,id) {
	
	let ans_json=[];
	
	const ans= await axios.get("https://api.yelp.com/v3/businesses/"+id, {
		headers: { 'Authorization':header_str,'Access-Control-Allow-Origin':'*' }
	}).then( function (response) {
									//console.log("response",response.data) ;
									let val=response.data
									
									
									callReviewApi(val,id,res)
									
			/*						
							
			   ans_json={"id":id,
						 "name":val.name,
						 "address":val.location.display_address,
						 "category":val.categories ,
						 "phone" :val.display_phone ,
						 "price" : val.price,
						 "stat"  : val.hours,
						 "business_url" : val.url,
						 "img_url":val.photos,
						 "lat":val.coordinates.latitude,
						 "lng":val.coordinates.longitude
									
							}  ;
							
			ans_json=JSON.stringify(ans_json);
							
			 console.log("stored data ", ans_json) ;
			 res.send(ans_json);
			  
*/			  
			   
		   } ) ;
									
	
	
}//getCardDetails function ends.



//call review api
async function callReviewApi(val,id,res) {
	
	let ans_json=[]
	
 await axios.get("https://api.yelp.com/v3/businesses/"+id+"/reviews", {
		headers: { 'Authorization':header_str,'Access-Control-Allow-Origin':'*' }
	}).then( function (ans) {
		
		
		    //console.log("Reviews data",ans.data)
			
			//console.log("extracted data",ans.data.reviews[0]['id'])
			
			
			
			
			ans_json={"id":id,
						 "name":val.name,
						 "address":val.location.display_address,
						 "category":val.categories ,
						 "phone" :val.display_phone ,
						 "price" : val.price,
						 "stat"  : val.hours,
						 "business_url" : val.url,
						 "img_url":val.photos,
						 "lat":val.coordinates.latitude,
						 "lng":val.coordinates.longitude,
						 "review_data":ans.data.reviews
						 
									
							}  ;
							
			ans_json=JSON.stringify(ans_json);
							
			// console.log("stored data ", ans_json) ;
			 res.send(ans_json);
		
		
	});
	
	
	
	
	
}//main function ends.






async function getAutocomplete(auto_res, auto_str) {
	
	
await	axios.get("https://api.yelp.com/v3/autocomplete?text="+auto_str, {
		headers: { 'Authorization':header_str,'Access-Control-Allow-Origin':'*' }
	}).then( function (ans) {
		
		
		console.log("auto res",ans.data)
		
		let auto_data=JSON.stringify(ans.data)
		
		auto_res.send(auto_data)
		
		
	})
	
	
	
	
	
} //auto end







const modules1 = {callYelpAPI,getGeolocation, getCardDetails,getAutocomplete}
export default modules1;


