import { Component, OnInit } from '@angular/core';

var output = console.log

// referenced https://angular.io/start/start-forms for how forms and .ts file works.


import { FormBuilder, FormControl, FormGroup,Validators } from '@angular/forms';
//import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { switchMap, debounceTime, tap, finalize } from 'rxjs/operators';
import { Router } from '@angular/router';

import { FormsModule }   from '@angular/forms';

import { HttpClient } from '@angular/common/http';
import { Host, Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

import {AfterViewInit, ElementRef, ViewChild} from '@angular/core';
import { MatTabsModule } from '@angular/material/tabs'; 


//import { SocialAuthService } from 'angularx-social-login';
//import { FacebookLoginProvider } from 'angularx-social-login';
//import { SocialUser } from 'angularx-social-login';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {formatDate} from '@angular/common'
import { DatePipe } from '@angular/common'
import { ReservationComponent } from '../reservation/reservation.component'


import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { distinctUntilChanged, filter } from 'rxjs/operators';


@Component({
  selector: 'app-my-dashboard',
  templateUrl: './my-dashboard.component.html',
  styleUrls: ['./my-dashboard.component.css']
})



@Injectable()

export class MyDashboardComponent implements OnInit {



//currentDate = new Date();
reservation_object:any;
mapOptions: google.maps.MapOptions ;
marker: google.maps.LatLngLiteral

global_string : string=""
lat_pos:number
lng_pos:number
reservation_form: FormGroup
searchForm: FormGroup;
keyword : string;
location : string;
category: string;
check:boolean ;
distance: string;
jData=[];

coord : string;
coord_arr : string[];
cat:string;
long : string;
lat : string;
safe_url: SafeUrl[]=[];
//url:URL;
//myTable : string;
bool_val:boolean;

date_stamp : string[]

card_keys=[]
rev=[n]

addr=""
post_bus_name=""								 
phone=""
price=""
stat=""
bus_url=""
photo=""
name_str=""

email: Boolean;
udate:Boolean;
utime:Boolean;
umin:Boolean;
auto_data=[]
currentDate=this.datePipe.transform(new Date(), 'yyyy-MM-dd');

@ViewChild('myTable') myTable: ElementRef;
@ViewChild('dataInfo') dataInfo: ElementRef;
@ViewChild('matData') matData: ElementRef;
@ViewChild('statID') statID: ElementRef;
@ViewChild('final_card') final_card: ElementRef;
@ViewChild('modalClose') modalClose: ElementRef;
@ViewChild('book_res') book_res: ElementRef;
@ViewChild('cancel_res') cancel_res: ElementRef;
@ViewChild('auto_input') auto_input: ElementRef;
@ViewChild('loc_id_val') lock_id_val: ElementRef;



searchMoviesCtrl = new FormControl();
  filteredMovies: any;
  isLoading = false;
  errorMsg!: string;
  minLengthTerm = 3;
  selectedMovie: any = "";

cat_arr=[]
txt_arr=[]







imagSource="assets/img/city.jpg";

//jsonObject: JSON;

res_json=[];


card_data: {
    [key: string]: any,
  }

newJson: {
    [key: string]: any,
  }


get primEmail(){
	return this.reservation_form.get('primaryEmail')
  }
  
get userDate(){
	return this.reservation_form.get('user_date')
  }
  
get userTime(){
	return this.reservation_form.get('user_time')
  }
  
get userMin(){
	return this.reservation_form.get('user_minutes')
  }


//autocomplete

onSelected() {
    console.log(this.selectedMovie);
    this.selectedMovie = this.selectedMovie;
  }

  displayWith(value: any) {
    return value?.Title;
  }

  clearSelection() {
    this.selectedMovie = "";
    this.filteredMovies = [];
  }
















constructor(
    private formBuilder: FormBuilder ,
	private http: HttpClient,
	private sanitizer: DomSanitizer,
	//private authService: SocialAuthService,
	//private url:URL,
	private datePipe: DatePipe
	//private reservation_form: FormBuilder
	
	
		
	
	){}


  ngOnInit(): void {
  
	//this.loc_id_val.nativeElement.setAttribute('required');
   this.searchForm = this.formBuilder.group({ keyword: '', location:'', distance:'10', category:'Default', check:''});
   
	this.keyword='';
	this.location='';
	this.category='';
	this.check=false;
	this.distance='';
	
	
	
	this.reservation_form=this.formBuilder.group( {     

											    primaryEmail: ['',[Validators.required,Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$"),Validators.email]],
												
												
												user_date :['',Validators.required],
												user_submit:['Submit',Validators.required],
												user_time:['',Validators.required],
												user_minutes:['',Validators.required]



												})
	
	
  }


//autocomplete

  yelpAutocomplete() {

this.searchMoviesCtrl.valueChanges
      .pipe(
        filter(res => {
          return res !== null && res.length >= this.minLengthTerm
        }),
        distinctUntilChanged(),
        debounceTime(1000),
        tap(() => {
          this.errorMsg = "";
          this.filteredMovies = [];
          this.isLoading = true;
        }),
        switchMap(value => this.http.get('https://assignment-8-368122.wl.r.appspot.com/autocomplete/'+value)
          .pipe(
            finalize(() => {
              this.isLoading = false
            }),
          )
        )
      )
      .subscribe((data: any) => {
    
		
		let some_val=data
		
		console.log(data['categories'][0]['title'])
		console.log(data['terms'][0]['text'])
		
		let k=0;
		for( let i in data['categories']) {
		this.cat_arr[k]=data['categories'][i]['title']
		k=k+1;
		
		}
		
		for( let i in data['terms']) {
		this.cat_arr[k]=data['terms'][i]['text']
		k=k+1;
		
		}
		
		
		
      });









}// yelp end










get mailVal() {

return this.email ;
}


reserveSubmit(val) {


	if (this.primEmail.invalid) {
	console.log("mail invalid")
	this.email=true;
	this.reservation_form.get('primaryEmail').markAsTouched({ onlySelf: true });

	} else this.email=false;

	if(this.userDate.invalid) {
	console.log("date invalid")
	this.udate=true;
	this.reservation_form.get('user_date').markAsTouched({ onlySelf: true })
	} else this.udate=false

	if(this.userTime.invalid) {
	console.log("time  hour invalid")
	this.utime=true;
	this.reservation_form.get('user_time').markAsTouched({ onlySelf: true })
	} else this.utime=false

	if(this.userMin.invalid) {
	console.log("Min invalid")
	this.umin=true;
	this.reservation_form.get('user_minutes').markAsTouched({ onlySelf: true })
	}else this.umin=false



	if(!this.email && !this.udate && !this.utime && !this.umin )  {


	this.bookReservation(this.primEmail.value,this.userDate.value,this.userTime.value,this.userMin.value)



	}



}





bookReservation(u_mail,u_date,u_time,u_min) {

console.log("yup")

alert("Reservation created!!")
this.modalClose.nativeElement.click() ;

console.log("umail is",u_mail,u_date,u_time,u_min)

let userObject=JSON.stringify({"name":this.name_str, "user_date":u_date, "user_hour":u_time, "user_min":u_min,"mail":u_mail})

if ( (localStorage.getItem("all_strings"))!=null &&(localStorage.getItem("all_strings"))!=undefined) {

this.global_string=localStorage.getItem("all_strings");

}


localStorage.setItem(this.name_str, userObject);




this.global_string=this.global_string+','+this.name_str

localStorage.setItem("all_strings",this.global_string);
  
console.log("data is ",localStorage.getItem(this.name_str))
console.log("global string ",localStorage.getItem("all_strings"))

this.book_res.nativeElement.style.display="none";

this.cancel_res.nativeElement.style.display="inline-block";
 
 

}


resetModal() {

this.reservation_form.reset();

}




cancelReservation() {


this.book_res.nativeElement.style.display="inline-block";

this.cancel_res.nativeElement.style.display="none";

alert("Reservation cancelled!!");


localStorage.removeItem(this.name_str);

this.global_string=localStorage.getItem("all_strings")
console.log("in cancel from local storage all string",this.global_string)
let str_store=(this.global_string).split(',')
this.global_string=""

		for (let i in str_store) {

		console.log("i",i)
		if(str_store[i]!=this.name_str && str_store[i]!=undefined && str_store[i]!=null) {
			this.global_string=this.global_string+','+str_store[i]
			console.log("in for loop, global str", str_store[i],this.global_string)
			

		}


		}
		

localStorage.setItem("all_strings",this.global_string);
this.global_string=localStorage.getItem("all_strings")

console.log("global str after cancel reser",this.global_string)

// call update booking routing


}










// referenced from stack overflow : https://stackoverflow.com/questions/57743966/getting-unsafe-url-error-while-displaying-image

imageUrlSanitize(imgURL: string): SafeUrl {
    return this.sanitizer.bypassSecurityTrustUrl(imgURL);
}



goBack() {

this.myTable.nativeElement.style.display="table";
this.final_card.nativeElement.style.display="none";



}




 onSubmit(userData:any) {
    
	this.final_card.nativeElement.style.display="none"
	//console.log('keyword', userData.keyword);
	this.keyword=userData.keyword;
	this.distance=userData.distance;
	this.category=userData.category;
	this.location=userData.location;
	this.check=userData.check;
    
	console.log('keyword', this.keyword);
	console.log('distance', this.distance);
	console.log('category', this.category);
	console.log('Location', this.location);
	console.log('Check', this.check);
		
	this.dataInfo.nativeElement.style.display="none";
	
	if(this.check) {
	this.getIPinfo(userData) ;
	
	} 

	else {
	
	 this.newJson={"keyword":this.keyword,"distance":this.distance, "category":this.category, "latitude":"", "longitude":"", "loc":this.location} ;
	 this.requestForYelp(this.newJson) ;
	
	
	}
	
	
  }



// function to get ipinfo 
getIPinfo(userData : any)  {


const req=this.http.get('https://ipinfo.io/json?token=your token');

req.subscribe(response=>{  this.jData=(response as any);
						   console.log("ipInfo", typeof this.jData);
						   for(let key in this.jData) {
						   
						   if(key=='loc') {
						   	

						   this.coord=this.jData[key];
						   this.coord_arr=this.coord.split(',');
						  
						   this.lat=this.coord_arr[0];
						   this.long=this.coord_arr[1];
						   
						   
						   this.newJson={"keyword":this.keyword,"distance":this.distance, "category":this.category, "latitude":this.lat, "longitude":this.long} ;
						   
						   
						   
						   //console.log("lat,long ",this.lat,this.long);
						   this.requestForYelp(this.newJson);
						   
						   
						   }
    
							}
							
						});
 
 

}



//function to call backend
requestForYelp(jsonData : any) {

const req_str=JSON.stringify(jsonData);
const client_req=this.http.get('https://assignment-8-368122.wl.r.appspot.com/searchFor/yelpAPI/'+req_str) ;
let a=0

client_req.subscribe( response=> { console.log('response from backend', response)
									this.res_json= response as any
									//console.log('response from backend', this.res_json.length)
									if ( this.res_json==null || this.res_json.length==0  || this.res_json.length==undefined) {
									console.log('i am here')
									this.displayNoData();
									
									}
									
										else {
										
										let k=0
										for (let i in this.res_json) {
										
										
										
										this.safe_url[k]=this.imageUrlSanitize(this.res_json[i]['img_url'])
										
										k=k+1
										
										}
										this.myTable.nativeElement.style.display="table" ;
									
										console.log("safe url", this.safe_url)
								  }
	

							});


}

// Displaying information - no data available.

displayNoData() {

this.myTable.nativeElement.style.display="none";
this.dataInfo.nativeElement.style.display="block" ;

}



// disable/enable location via checkbox
evaluateCheck(checkEvent:any) {

   if(checkEvent.target.checked){ 
   
	console.log("Hurray checked");
	this.searchForm.controls['location'].setValue("");
	this.searchForm.controls['location'].disable();
   }
   else {
   
   this.searchForm.controls['location'].enable();
   console.log("Now unchecked");
   }
   
   
   
}

// Clear button function

resetPage() {

	this.searchForm.reset();
	this.searchForm.controls['location'].enable();
	this.searchForm.controls['distance'].setValue("10");
	this.searchForm.controls['category'].setValue("Default");
	//this.myTable.get[''].setValue("Default");
	this.myTable.nativeElement.style.display="none";
	this.dataInfo.nativeElement.style.display="none";
	this.final_card.nativeElement.style.display="none"


}


// make backend request for particular business search card.

callCardDetails(i:number) {



this.myTable.nativeElement.style.display="none" ;
this.final_card.nativeElement.style.display="block"

console.log("Got number",i);
console.log("id",this.res_json[i]['id'], this.res_json[i]['bus_name'] ) ;

const card_req=this.http.get('https://assignment-8-368122.wl.r.appspot.com/searchFor/cardDetails/'+this.res_json[i]['id']) ;
let a=0

card_req.subscribe( response=> {  console.log("card details",response)
									
								 let temp_data=response as any
								 
								// let temp_str=""
								 
								 this.post_bus_name=temp_data['name']
								 this.name_str=temp_data['name']
								 
								 if(localStorage.getItem(this.name_str)==null || localStorage.getItem(this.name_str)==undefined ) {						 
								 this.book_res.nativeElement.style.display="inline-block"
								 this.cancel_res.nativeElement.style.display="none"
								 				 
								 }
								 else {
								 
								 this.book_res.nativeElement.style.display="none"
								 this.cancel_res.nativeElement.style.display="inline-block"
								 
								 
								 }
								 
								 this.post_bus_name= encodeURIComponent(this.post_bus_name)
								 
								 this.lat_pos=temp_data['lat'];
								 this.lng_pos=temp_data['lng'];
								 
								 this.mapOptions = {
												center: { lat: this.lat_pos, lng: this.lng_pos },
												zoom : 14
												}
												
								this.marker = {
											 'lat': this.lat_pos, 'lng':this.lng_pos }
										
	
//------------------------------------------------------------


this.rev=temp_data['review_data']
let time_str=""

this.date_stamp=[]
//console.log("REV NAME", rev[0]['user']['name'])
//console.log("REV RATING", rev[0]['rating'])
//console.log("REV RATING", rev[0]['text'])
//console.log("REV RATING", rev[0]['time_created'])

for (let i in this.rev) {
 
 time_str=(this.rev[i]['time_created']).split(' ')
 time_str=(this.rev[i]['time_created']).split(' ')
 console.log("time_str",time_str)
this.rev[i]['time_created']=time_str[0]

}


console.log("CRPPED STRING", this.date_stamp)


//------------------------------------------------------------



	
								 
//-----addr------------								 
if("address" in temp_data) {
								   
	if(temp_data['address']!="" &&  temp_data['address']!=undefined  && (temp_data['address']).length!=0 ) {

		 this.addr= ((temp_data['address']).toString()).replace(',',' ');
									   
		//console.log("addr", addr)
									   
		} 
		else
		this.addr="N/A"
																	   
} else
		this.addr="N/A"

//--------------cat-----------------
let k=0 
this.cat=" " 
//console.log("cat",temp_data['category'][0]['title'])  
 if("category" in temp_data) {
								   
	if(temp_data['category']!="" &&  temp_data['category']!=undefined  && (temp_data['category']).length!=0 ) {
	
			for (let i in temp_data['category']) {	
			
			 this.cat=this.cat+temp_data['category'][i]['title']+' | '
			 
			
			}//end for
			
			this.cat = this.cat.substring(0, this.cat.length - 2);
			
		 console.log("cat",this.cat)							   
		} 
	else 
	this.cat="N/A"
																	   
}  else 
	this.cat="N/A" 
  
//----------------phone-------------------------------------------
  
  if("phone" in temp_data) {
								   
	if(temp_data['phone']!="" &&  temp_data['phone']!=undefined  && (temp_data['phone']).length!=0 ) {

		 this.phone= (temp_data['phone']);
									   
		//console.log("phone", phone)
									   
		}
	else 
	this.phone="N/A"
																	   
}else 
	this.phone="N/A" 

//--------------------------price---------------
  
if("price" in temp_data) {
								   
	if(temp_data['price']!="" &&  temp_data['price']!=undefined  && (temp_data['price']).length!=0 ) {

		 this.price= (temp_data['price']);
									   
		//console.log("price", price)
									   
		}
		else
		this.price="N/A"
																	   
} else
this.price="N/A"

//---------------------status--------------------
if("stat" in temp_data) {
								   
	if(temp_data['stat']!="" &&  temp_data['stat']!=undefined  && (temp_data['stat']).length!=0 ) {

		 this.bool_val= temp_data['stat'][0]['is_open_now'];
		 if(this.bool_val==true){
		 this.stat="Open Now"
		 this.statID.nativeElement.style.color="green";
		 }
		 else if(this.bool_val==false){
		 this.stat="Closed"
		 this.statID.nativeElement.style.color="red";
		 }
		 else {
		 this.stat="N/A";
		 this.statID.nativeElement.style.color="grey";
		 }
		
		console.log("bool", this.bool_val, typeof this.bool_val)
		//console.log("stat", stat)
									   
		}
		else{
		this.stat="N/A"
		this.statID.nativeElement.style.color="grey";
		}
																	   
} 
else{
this.stat="N/A"
this.statID.nativeElement.style.color="grey";
}


//----------------------link---------------------------------

if("business_url" in temp_data) {
								   
	if(temp_data['business_url']!="" &&  temp_data['business_url']!=undefined  && (temp_data['business_url']).length!=0 ) {

		 this.bus_url= (temp_data['business_url']);
									   
		//console.log("bus_url", bus_url)
									   
		}
		else 
		this.bus_url="N/A"
																	   
} 	
else
this.bus_url="N/A"
//-----------------------photos---------------------------------
	
	if("img_url" in temp_data) {
								   
	if(temp_data['img_url']!="" &&  temp_data['img_url']!=undefined  && (temp_data['img_url']).length!=0 ) {
	
			 this.photo=temp_data['img_url']

			
		 //console.log("photo",photo)							   
		} else
		this.photo="N/A"
																	   
}  else 
this.photo="N/A" 

console.log("photo", this.photo,this.photo[0],this.photo[1]) 
//-------------------------------------------------------------------------







								} ) //end response





}


}// main class end
