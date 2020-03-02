//var polling;
// add listner to pebble ,if message sent from pebble this will be called
// message in settings we combine msg:0, payload.msg will extract value with key 0
Pebble.addEventListener("appmessage",
  function(e) {
    if(e.payload){
      if(e.payload.msg === 'show'){
        sendToServer('show');
        //polling = setInterval(function(){
        //sendToServer('show');}, 5000);
      }else if(e.payload.msg === 'time'){
        sendToServer('time');
     //   polling = setInterval(function(){
     //  sendToServer('time');}, 5000);         
      }else{
     //   clearInterval(polling);
        sendToServer(e.payload.msg);
      }
    }   
  }
);



function sendToServer(command) {
	var req = new XMLHttpRequest();
	var ipAddress = "165.123.223.182"; // Hard coded IP address
	var port = "3001"; // Same port specified as argument to server
	var url = "http://" + ipAddress + ":" + port + "/" + command;
	var method = "GET";
	var async = true;
  var msg = "";
	req.onload = function(e) {
    // see what came back
    
    var response = JSON.parse(req.responseText);
    if (response) {
        if (response.curT || response.runtime || response.trend || response.error || 
            response.pause || response.resume || response.convert || response.help) {
          msg = response[Object.keys(response)[0]];
          Pebble.sendAppMessage({ "0": msg});
        }else if(response.data){
           msg = response.data;
           msg = msg.split('#').join('\n');
           Pebble.sendAppMessage({ "1": msg});
        }else {
           msg = "Undifined message recieved from server";
           Pebble.sendAppMessage({ "2": msg});
        }
     }  // sends message back to pebble
	};
        req.open(method, url, async);
        req.send(null);
  // Error handler for server, if it does not recieve any response from server
  // up to 3 seconds , it will tell pebble , server no response
  setTimeout(function(){if(msg === ""){
    Pebble.sendAppMessage({ "3": "No response from server!"});}
  }, 3000);
}