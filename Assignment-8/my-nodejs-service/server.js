import express from 'express'
import cors from 'cors'
import bodyParser from 'body-parser'
import  modules1 from './helperFile.js'

import fb from 'fb'





const app = express();

const router = express.Router();


app.get('/', (req, res) => {
  res.send('Hello from App Engine!');
});


app.use(cors());




app.get('/searchFor/yelpAPI/:data', async function (req, res) {
	
    console.log('request is',JSON.parse(req.params.data));

	var  json_val=JSON.parse(req.params.data) ;
	
	if (json_val.latitude==""  &&  json_val.longitude=="") {
		console.log('i am here');
		
		modules1.getGeolocation(json_val.loc,res,JSON.parse(req.params.data)) ;
			
	}
	
	else {
	// for ipinfo recieved info
	modules1.callYelpAPI(res, JSON.parse(req.params.data), "", "")

	}
 
	 //console.log(orig_res);
    
    // res.send("ok");
    // if (origRes)
    //     return res.status(200).json(origRes);
}) ;





app.get('/searchFor/cardDetails/:card_data', async function (req, res) {
	
	 console.log('request is',(req.params.card_data))
	 
	 modules1.getCardDetails(res,(req.params.card_data)) ;
	
	
	
	//res.send({"st":"ok"})
	
	
});




app.get('/autocomplete/:auto_str', async function (req, res) {
	
	 
	 
	 modules1.getAutocomplete(res,(req.params.auto_str)) ;
	
	
	
	//res.send({"st":"ok"})
	
	
});













export default router ;












// Listen to the App Engine-specified port, or 8080 otherwise
const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}...`);
});