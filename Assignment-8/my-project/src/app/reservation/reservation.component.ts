import { Component, OnInit } from '@angular/core';
import {AfterViewInit, ElementRef, ViewChild} from '@angular/core';
import { ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {

@ViewChild('display_info') display_info: ElementRef;
@ViewChild('res_table') res_table: ElementRef;


bus_name=""
user_date=""
user_time=""
user_email=""
count=0
arr=new Array()
track_counter=0
second_counter=0
con_bool=false


some_counter=0;

global_string:string=""
 
temp_name=""

router : Router


 constructor() {
 
 // this.cdr.detectChanges()
}
 
 
 
  ngOnInit(): void {
  
  
  
console.log("log all string", localStorage.getItem("all_strings"))

let temp=(localStorage.getItem("all_strings"))

if (temp!=null && temp!=undefined) {

		let temp_str1=temp.split(',')
		let temp_str2=(temp_str1.toString()).replace(/,/g, '')

		console.log("m",temp_str2, temp_str2.length)

		if(localStorage.getItem("all_strings")!=null  && temp_str2!="" && temp_str2.length!=0) {

			 // this.display_info.nativeElement.style.display="none";
			  //this.res_table.nativeElement.style.display="block";
			  this.track_counter=1;
			  let str_val1=localStorage.getItem("all_strings")
			  
			  let str_val=str_val1.split(',')
			  
					  for (let i in str_val) {
					  
					  if(str_val[i]!=null &&  str_val[i]!=undefined && str_val[i]!="" ) {
					  
					  
					  let obj=localStorage.getItem(str_val[i])
					  let json_obj=JSON.parse(obj);
					  
					  this.arr[this.count]=json_obj
					  this.count=this.count+1;
					  
					
					  
					  }
					  
					  console.log("vals",this.user_date,this.user_time,this.user_email)
				  
					  }//end for   */
					  
					  
					  

		  }// if close
		  
		  else {
		  
		  this.track_counter=0;
		  //this.display_info.nativeElement.style.display="block";
		  //this.res_table.nativeElement.style.display="none";

		  
		  
		  }
		  
  }  
  
  else {this.track_counter=0}
  
  let local=localStorage
  for (var key in local) {
  console.log(key);
//Do something with key, access value by local[key]
}


  
  
  
  }//init end
  
  
 
 
 
 
 
 deleteBookings(num,bool_val) {
 
 
 
 localStorage.removeItem(this.arr[num]['name'])
 
 console.log("--------name---------",this.arr)
 
 this.temp_name=this.arr[num]['name']
 
 console.log("name",this.temp_name)
 
 let tempo=this.arr
 
 delete tempo[num];
 
 this.arr=tempo
 
 
 
 
 
 
 this.global_string=localStorage.getItem("all_strings")
 
 
console.log("after deleting take all string now",this.global_string)


let str_store=(this.global_string).split(',')

this.global_string=""

		for (let i in str_store) {

		
		if(str_store[i]!=this.temp_name  && str_store[i]!=undefined && str_store[i]!=null) {
		
			this.global_string=this.global_string+','+str_store[i]
			//console.log("in for loop, global str", str_store[i],this.global_string)
			

		}


		}
		

localStorage.setItem("all_strings",this.global_string);

//this.ngOnInit()
//window.location.reload()
this.reloadCurrentRoute()

this.global_string=localStorage.getItem("all_strings");
console.log("after deletion all_string",this.global_string)
 
 alert("Reservation cancelled!!")
 
 
  //this.cdr.detectChanges()
 
 
 
 
 
 
 
  
 } 
 
 
 
 
 reloadCurrentRoute() {
    let currentUrl = this.router.url;
    this.router.navigateByUrl('/bookings', {skipLocationChange: true}).then(() => {
        this.router.navigate([currentUrl]);
    });
}
 
 

}