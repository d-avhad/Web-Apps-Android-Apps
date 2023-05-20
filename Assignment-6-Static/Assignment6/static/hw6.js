lat=""
lng=""
ip_lat=""
ip_long=""
backend_data=""
click_count=0

// Method for disabling Location Box on selecting checkbox
function toggleLocationBox(st,id_name) {
         document.getElementById(id_name).disabled =st	 
		 if(document.getElementById(id_name).disabled)
		 {
			 document.getElementById(id_name).style.background = "#DCDCDC"
			 document.getElementById(id_name).value=""
		 }
		 
		 else
			 document.getElementById(id_name).style.background ="white"

    
  }
  
  
// Method for reseting and setting default values onclicking clear Button

function defaultFields() {
	
	
	document.getElementById("main_form").reset()
	document.getElementById('location').disabled=false
	document.getElementById('location').style.background ="white"
	
	document.getElementById("id_card1").style.visibility="hidden"
	document.getElementById("data_table").style.visibility="hidden"
	
	
	document.getElementById("id_card1").style.visibility="hidden"

	document.getElementById("uni_p0").style.visibility="hidden"
	document.getElementById("uni_p1").style.visibility="hidden"
	document.getElementById("uni_p2").style.visibility="hidden"



	document.getElementById("uni_div0").style.visibility="hidden"
	document.getElementById("uni_div1").style.visibility="hidden"
	document.getElementById("uni_div2").style.visibility="hidden"





	document.getElementById("label_info1").style.visibility="hidden"
	document.getElementById("card_info").style.visibility="hidden"


	document.getElementById('card_price').style.visibility="hidden"
	document.getElementById('label_price1').style.visibility="hidden"


	document.getElementById('card_trans').style.visibility="hidden"
	document.getElementById('label_t1').style.visibility="hidden"


	document.getElementById('card_num').style.visibility="hidden"
	document.getElementById('label_p1').style.visibility="hidden"


	document.getElementById('card_addr').style.visibility="hidden"
	document.getElementById('label_a1').style.visibility="hidden"


	document.getElementById('label_s1').style.visibility="hidden"
	document.getElementById('card_status').style.visibility="hidden"


	document.getElementById('card_cat').style.visibility="hidden"
	document.getElementById('label_c1').style.visibility="hidden"
	
	
	t = document.getElementById("data_table");
				
		row_count=t.rows.length
		
				if (row_count>1){
				
				t_d=document.getElementById('data_table').getElementsByTagName('tbody')[0]
				t_d.innerHTML =''
				}
	
		
		
  }
  
// Method to get parse response of google geocoding api - for geoloaction 	

function getGeoLocation() {
	
	
	if (req.readyState == 4) {
			if (req.status == 200)
			{
			doc = JSON.parse(req.responseText) 
			lat=doc.results[0].geometry.location.lat
			lng=doc.results[0].geometry.location.lng
			console.log(doc)
			console.log("the value is")
			console.log(lat,lng)
			
			getdata()
			}
			else
			console.log("Not ready")
				
											
		}
		
	
  }	
  
// Method to get parse response of ipinfo.io api  
  
function getIPinfo() {
	
	console.log("I am in IPINFO")
	
	if (ip_req.readyState == 4  && ip_req.status == 200) {
			
			ip_doc = JSON.parse(ip_req.responseText)
			ip_doc=ip_doc["loc"]
			ip_doc= ip_doc.split(",");
			ip_lat=ip_doc[0]
			ip_long=ip_doc[1]
			
			console.log(ip_lat,ip_long)
			//console.log(ip_doc)
			
			getdata()
			
	}
	
	
	
 }
 
 
 // function to get all input data in one object
 
 function getdata() {
	
	obj={}

	obj["Keyword"]=document.getElementById('keyword').value
	obj["Distance"]=document.getElementById('distance').value
	obj["Category"]=document.getElementById('category').value

	if( (document.getElementById('location').disabled==false))
	{
	
	obj["Lat"]=lat
	obj["Lng"]=lng		
	
	
	}
	
	else
	
	{		
	obj["Lat"]=ip_lat
	obj["Lng"]=ip_long
		
	}

	
	console.log("Final Data is")
	console.log(obj)
	
	obj=JSON.stringify(obj)
	
	requestBackend("/requ/"+obj)
	
	
	
	
	
	
	
}
 
 
// send http request to geocoding api and ipinfo.io api & then call getdata()
  
function helperfunc() {
	console.clear()
	console.log("I am here")

	if ( (document.getElementById('location').disabled==false) &&  (document.getElementById('location').value!=null) )
	{
		
		uri= document.getElementById('location').value;
		addr= encodeURIComponent(uri);	
		console.log(addr)


		req = new XMLHttpRequest();
		req.open("GET", "https://maps.googleapis.com/maps/api/geocode/json?address="+addr+"&key=your key", true); 
		req.onreadystatechange = getGeoLocation; 
		req.send(); 

	}

	else
	{

	ip_req=new XMLHttpRequest();
	ip_req.open("GET", "https://ipinfo.io/json?token=your token",true)

	ip_req.onreadystatechange = getIPinfo; 
	ip_req.send();
		
		
		
	}
	
	
  }
	


function backendCallback()

{
	
	if (r.readyState ==4 && r.status == 200) {
								
		b_data=r.responseText
		backend_data=JSON.parse(b_data)
		count=0
	
		
		t = document.getElementById("data_table");
				
		row_count=t.rows.length
		
				if (row_count>1){
				
				t_d=document.getElementById('data_table').getElementsByTagName('tbody')[0]
				t_d.innerHTML =''
				}
			
		
		for (let x in backend_data)
		{
			
			t_dis=backend_data[x]['distance']
			t_dis=(t_dis/1609.34).toFixed(2)
			t_img=backend_data[x]['img']
			t_name=backend_data[x]['name']
			t_rating=backend_data[x]['rating']
			
			//t = document.getElementById("data_table");
			t = document.getElementById('data_table').getElementsByTagName('tbody')[0]
					
			if (t_dis.length!=0 && t_rating.length!=0 && t_name.length!=0 )
			{
				
				document.getElementById("data_table").style.visibility='visible'
			
				
				row = t.insertRow(count);
				cell0 = row.insertCell(0);
				cell1 = row.insertCell(1);
				cell2 = row.insertCell(2);
				cell3 = row.insertCell(3);
				cell4 = row.insertCell(4);
				cell5=  row.insertCell(5);
				
				cell0.innerHTML = (count+1);
				cell1.innerHTML = "<img src='"+t_img+"' alt='Image' width='100' height='100'/>";
				txt='<a href="javascript:getbusdetails("'
				txt1='")>'
				cell2.innerHTML = '<a href="#id_card1" style="text-decoration: none; color:#000000;" onclick="javascript:getbusdetails('+(count+1)+')" >'+t_name+'</a>'
				
								
				cell3.innerHTML = t_rating;
				cell4.innerHTML = t_dis;
				
				cell5.innerHTML=t_name
				cell5.style.visibility="hidden"
							
				count=count+1
			
			
				console.log(backend_data[x]['distance']);
			}
			
		}
								
		
		
		
		
		
	}
		
	
}


	
 
 function requestBackend(url) {
	 
	 r = new XMLHttpRequest()	
	 r.open("GET", url, true)	 
	 r.onreadystatechange = backendCallback;
	 r.send();
	 
 }


 
function  getbusdetails(n)
{
	url="/bus/"+n
	console.log("the link val",n,url)
	
	b_r = new XMLHttpRequest()	
	b_r.open("GET",url, true)	
	
	b_r.onreadystatechange = display_bus_card;
	b_r.send();
	
	
}
 

function display_bus_card () {
	
	
	if (b_r.readyState ==4 && b_r.status == 200) {
								
		c_data=JSON.parse(b_r.responseText)
		
		console.log("card details",c_data)
		
		//-----------------------------------------------------------
		cat_val=c_data["categories"]
		cat_str=""
		
		
		
		if (cat_val!=null && cat_val.length!=0)  //setting category values
		{
			
			for (let y=0 ; y<(cat_val.length);y++)
			{
				cat_str+=cat_val[y]["title"]
				
				
			    if(y < cat_val.length-1){
				cat_str+='|'  }
								
			}
			
			if (document.getElementById('card_cat').innerHTML!=null){
			document.getElementById('card_cat').innerHTML=cat_str}
			document.getElementById('label_c1').style.visibility="visible"
			document.getElementById('card_cat').style.visibility="visible"
		
		}
		
		else {
			
			document.getElementById('card_cat').innerHTML=""
			document.getElementById('label_c1').style.visibility="hidden"
			
			
		}
		
		//---------------------------------------------------------------------
		st_val=c_data["is_closed"]  //setting status value
		
		
		if (st_val!=null && st_val.length!=0)
		{
			let str=(/^False$/i).test(st_val)
				if(str==true)
				{
								
					document.getElementById('card_status').innerHTML='Open Now'
					document.getElementById('card_status').style.background='#2E8B57'
								
				}
				else
				{
					document.getElementById('card_status').innerHTML='Closed'
					document.getElementById('card_status').style.background='#FF0000'
				}
				
			document.getElementById('label_s1').style.visibility="visible"
			document.getElementById('card_status').style.visibility="visible"
			
		}
		
		else {
			document.getElementById('card_status').innerHTML=""
			document.getElementById('label_s1').style.visibility="hidden"
			document.getElementById('card_status').style.visibility="hidden"
			
			
		}
		
		
		
		
		//------------------------------------------------------------------------
		addr_val=c_data["location"]["display_address"]   //setting location value
		addr_str=""
		
		
		if(addr_val!=null && addr_val.length!=0)
		{
			
			for (let k=0; k<addr_val.length; k++)
			{
				addr_str+=addr_val[k]

				
			}
			
			document.getElementById('card_addr').innerHTML=addr_str
			document.getElementById('label_a1').style.visibility="visible"
			document.getElementById('card_addr').style.visibility="visible"
			
			
			
		}
		
		else
		{
			document.getElementById('card_addr').innerHTML=""
			document.getElementById('label_a1').style.visibility="hidden"
			
		}
		
	//----------------------------------------------------------------------------------
	disp_phone=c_data["display_phone"]  //setting phone value
	
	
	if(disp_phone!=null && disp_phone.length!=0){
	console.log("disp_phone",disp_phone)
	document.getElementById('label_p1').style.visibility="visible"
	document.getElementById('card_num').innerHTML=disp_phone
	document.getElementById('card_num').style.visibility="visible"
	
	}
	else {
		document.getElementById('card_num').innerHTML=""
		document.getElementById('label_p1').style.visibility="hidden"
		
		
		
	}
	
	
	//-------------------------------------------------------------------
	trans_val=c_data["transactions"]  //setting transcation value
	
	
	
	trans_str=""
		
		if(trans_val!=null && trans_val.length!=0)
		{
			
			for (let k=0; k<trans_val.length; k++)
			{
				tmp=trans_val[k].charAt(0).toUpperCase()+ trans_val[k].slice(1)
				trans_str+=tmp
				
				
				if(k < trans_val.length-1){
				trans_str+='|'  }
				
			}
			document.getElementById('label_t1').style.visibility="visible"
			document.getElementById('card_trans').innerHTML=trans_str
			document.getElementById('card_trans').style.visibility="visible"
	
		}
		
		else {
			
			document.getElementById('card_trans').innerHTML=""
			document.getElementById('label_t1').style.visibility="hidden"
			
			
			
		}
	
	
	//------------------------------------------------------------------
	
	price_disp=c_data["price"]  //setting  price
	
	
	
	if(price_disp!=null && price_disp.length!=0) {
	document.getElementById('label_price1').style.visibility="visible"
	document.getElementById('card_price').innerHTML=price_disp 
	document.getElementById('card_price').style.visibility="visible"}
	
	else
	{
		document.getElementById('card_price').innerHTML=""
		document.getElementById('label_price1').style.visibility="hidden"
		
	}
	
	//--------------------------------------------------------------------
	
	info_disp=c_data["url"] //setting yelp info
	
	
	if (info_disp!=null && info_disp.length!=0)
	{	
	document.getElementById("card_info").style.visibility="visible"
	document.getElementById("card_info").href=info_disp
	document.getElementById("label_info1").style.visibility="visible"
	
	}
	
	else
	{
	 document.getElementById("card_info").innerHTML=""
	 document.getElementById("label_info1").style.visibility="hidden"
	 
		
		
	}
	
	
	
	//---------------------------------------------------------------------
	
	photo=c_data["photos"]
	img_src1='<img src="'
	img_src2='" style="width:190px; height:130px;"'
	
	
	if(photo!=null && photo.length!=0)
	{	
	
		for (let m=0; m<photo.length; m++) 
		{
			
		img_id='uni_div'+m
		p_id='uni_p'+m
		document.getElementById(img_id).src=photo[m]
		document.getElementById(p_id).style.visibility='visible'
		document.getElementById(img_id).style.visibility='visible'
				
		}
		
	}
	
	else
	{
		for (i=0; i<3; i++)
		{
		img_id='uni_div'+i
		p_id='uni_p'+i
		document.getElementById(img_id).innerHTML=""
		document.getElementById(p_id).style.visibility="hidden"
		}		
	}	
	
	//--------------------------------------------------------------
	//setting heading
	h_str=c_data["name"]
	document.getElementById("card_heading").innerHTML=h_str
	
	
	document.getElementById("id_card1").style.visibility="visible"
	
  }
}




//  Referred from w3 schools :https://www.w3schools.com/howto/tryit.asp?filename=tryhow_js_sort_table_number
// and added my own logic as well.

function sortTable(n,ans) {
  var table, rows, switching, i, x, y, shouldSwitch;
  
  
  
  
  if (click_count==0)
  {
			table = document.getElementById("data_table");
			  switching = true;
			  
			  while (switching) {
				
				switching = false;
				rows = table.rows;
				
				for (i = 1; i < (rows.length - 1); i++) {
				  
				  shouldSwitch = false;
				 
				  x = rows[i].getElementsByTagName("TD")[n];
				  y = rows[i + 1].getElementsByTagName("TD")[n];
				 
				  if (ans=='no'){
								  if (Number(x.innerHTML) > Number(y.innerHTML)) {
									
									shouldSwitch = true;
									break;
								    }
								}
				  else {
									g1=x.innerHTML
									g2=y.innerHTML
									console.log("char0",g1.charAt(51))
								  if (g1.toLowerCase() > g2.toLowerCase()) {
									 
									shouldSwitch= true;
										break;
									}
				  
						}
						
				}
				
				if (shouldSwitch) {
				  
				  rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
				  switching = true;
				}
			  }
			  
			  click_count=1;
			  
         }
		 
		 
	 else if (click_count==1)
	{
			table = document.getElementById("data_table");
			
			switching = true;
			 
			  
			  while (switching) {
				
				switching = false;
				rows = table.rows;
				
				for (i = 1; i < (rows.length - 1); i++) {
				 
				  shouldSwitch = false;
				 
				  x = rows[i].getElementsByTagName("TD")[n];
				  y = rows[i + 1].getElementsByTagName("TD")[n];
				 
				  if (ans=='no'){
								  if (Number(x.innerHTML) <Number(y.innerHTML)) {
									
									shouldSwitch = true;
									break;
								    }
								}
				  else {
								 g1=x.innerHTML
								 g2=y.innerHTML
								 console.log("char",g1.charAt(51))
								  if (g1.toLowerCase()< g2.toLowerCase()) {
									 //if so, mark as a switch and break the loop:
									shouldSwitch= true;
										break;
									}
				  
						}
						
				}
				if (shouldSwitch) {
				  /*If a switch has been marked, make the switch
				  and mark that a switch has been done:*/
				  rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
				  switching = true;
				}
			  }
			  
			  click_count=0;
		
		
		
	}
		
		
		
	table1 = document.getElementById("data_table")
	len1=	table1.rows.length;
	
	for (i=1; i<len1; i++)
	{
	some_val=table.rows[i].cells[0].innerHTML=(i)
	
	}
	
	
	
	
	
	
	
	
}
