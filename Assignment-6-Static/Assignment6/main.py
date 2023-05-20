from flask import Flask, Response
from flask_cors import CORS
import json

import requests



app = Flask(__name__, static_folder='static')
CORS(app)



name_rec={}
id_rec={}

@app.route("/")
def home():

    return app.send_static_file('index.html')
    
 
@app.route("/requ/<string:obj>",methods=['GET']) 
def req(obj):

    print("Pyhton recieved req",obj)
    
    json_obj = json.loads(obj)
            
    keyword=json_obj["Keyword"]
    category=json_obj["Category"]
    distance_miles=json_obj["Distance"]
    distance=float(float(distance_miles)*1609.34)
    radius=int(distance)
    lat=float(json_obj["Lat"])
    lng=float(json_obj["Lng"])
    print("type of object is ",type(json_obj))
    print("Extracted Data:")
    print("keyword",keyword)
    print("category",category)
    print("radius",radius)
    print("lat",lat)
    print("lng",lng)
    
    
    json_data=callyelpAPI(keyword,lat,lng,category,radius)
    
    return json_data

    


def callyelpAPI(keyword,lat,lng,category,radius):

    record=[]

    key ='your key'
      
    p={"term":keyword, "latitude":lat, "longitude":lng, "categories":category, "radius":radius}
       
    s = requests.Session()
    
    h = {'Authorization': 'Bearer {}'.format(key),'Access-Control-Allow-Origin':'*'}
    
    res=s.get("https://api.yelp.com/v3/businesses/search",headers=h,params=p)
    s.close() 
   
    
    json_res=json.loads(res.text)
    
    
    bus=json_res["businesses"]
    
    main_rec={}
    
    
    for i in range(0,len(bus)):
    
        rec={}
        
         
     
        if "image_url" in bus[i]:
            rec["img"]= bus[i]["image_url"]
            
                
        if "name" in bus[i] :
            rec["name"]=bus[i]["name"]
            name_rec[i]=bus[i]["name"]
            if "id" in bus[i]:
                id_rec[name_rec[i]]= bus[i]["id"]
            
        
        if "rating" in bus[i] :
            rec["rating"]=bus[i]["rating"]
        
        if "distance" in bus[i] :
            rec["distance"]=bus[i]["distance"]
            
        main_rec[i]=rec
            
        
    main_rec1= json.dumps(main_rec)  
    print(main_rec1)
    
    return main_rec1
    
 


@app.route("/bus/<string:st>",methods=['GET']) 
def bus_search(st):

    key ='your key'
      
    id1=""
    
    st=int(st)-1
    
    if st in name_rec:
        id1=id_rec[name_rec[st]]
    
    
    for i in range (0,len(name_rec)):
        if st is name_rec[i]:
            id1=id_rec[st]
        
    url="https://api.yelp.com/v3/businesses/"+id1
    s = requests.Session()
    
    
    
    h = {'Authorization': 'Bearer {}'.format(key), 'Access-Control-Allow-Origin':'*'}
    
    res=s.get(url,headers=h)
    s.close() 
   
    
    json_res=json.loads(res.text)
    
    print(json_res)
    
    return json_res
    


       
if __name__ == "__main__":
    app.run()
    
    
