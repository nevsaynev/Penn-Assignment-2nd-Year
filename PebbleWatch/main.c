#include <pebble.h>
#include <pebble_fonts.h>


static Window *window;
static TextLayer *hello_layer;
static SimpleMenuLayer *menu_layer;
static SimpleMenuSection  *section;

static char msg[100];

/* Call back function for menu items trend*/
static void trend_handler(int index, void* not_used){
  // if display layer is showing messages, it will hide layer without doing anything
  if(! layer_get_hidden((Layer*)hello_layer)){
     layer_set_hidden	((Layer*)hello_layer, true); 
  }else{
     DictionaryIterator *iter;
     app_message_outbox_begin(&iter);
     int key = 0;
    // send key 0 :value trend to the phone 
     Tuplet value = TupletCString(key, "trend");
     dict_write_tuplet(iter, &value);
    // clear display layer
     text_layer_set_text(hello_layer, "");
     app_message_outbox_send();
     layer_set_hidden	((Layer*)hello_layer, false);
  }
}
/* Call back function for menu items help*/
static void help_handler(int index, void* not_used){
  if(! layer_get_hidden((Layer*)hello_layer)){
     layer_set_hidden	((Layer*)hello_layer, true); 
  }else{
     DictionaryIterator *iter;
     app_message_outbox_begin(&iter);
     int key = 0;
    // send key 0 :value help to the phone 
     Tuplet value = TupletCString(key, "help");
     dict_write_tuplet(iter, &value);
    // clear display layer
     text_layer_set_text(hello_layer, "");
     app_message_outbox_send();
     layer_set_hidden	((Layer*)hello_layer, false);
  }
}

/* Call back function for menu items time*/
static void time_handler(int index, void* not_used){
  if(! layer_get_hidden((Layer*)hello_layer)){
     layer_set_hidden	((Layer*)hello_layer, true); 
  }else{
    DictionaryIterator *iter;
    app_message_outbox_begin(&iter);
    int key = 0;
    // send key 0 :value time to the phone
    Tuplet value = TupletCString(key, "time");
    dict_write_tuplet(iter, &value);
    // clear display layer
    text_layer_set_text(hello_layer, "");
    app_message_outbox_send();
    layer_set_hidden	((Layer*)hello_layer, false);
  }
}

/* Call back function for menu items data*/
static void data_handler(int index, void* not_used){
  if(! layer_get_hidden((Layer*)hello_layer)){
     layer_set_hidden	((Layer*)hello_layer, true); 
  }else{
      DictionaryIterator *iter;
      app_message_outbox_begin(&iter);
      int key = 0;
    // send key 0 :value data to the phone
      Tuplet value = TupletCString(key, "data");
      dict_write_tuplet(iter, &value);
    // clear display layer
      text_layer_set_text(hello_layer, "");
      app_message_outbox_send();
      layer_set_hidden	((Layer*)hello_layer, false);
  }
}

/* Call back function for menu items show*/
static void show_handler(int index, void* not_used){
  if(! layer_get_hidden((Layer*)hello_layer)){
     layer_set_hidden	((Layer*)hello_layer, true); 
  }else{
     DictionaryIterator *iter;
     app_message_outbox_begin(&iter);
     int key = 0;
    // send key 0 :value show to the phone
     Tuplet value = TupletCString(key, "show");
     dict_write_tuplet(iter, &value);
    // clear display layer
     text_layer_set_text(hello_layer, "");
     app_message_outbox_send();
     layer_set_hidden	((Layer*)hello_layer, false);
  }
}

/* Call back function for menu items convert*/
static void convert_handler(int index, void* not_used){
  if(! layer_get_hidden((Layer*)hello_layer)){
     layer_set_hidden	((Layer*)hello_layer, true); 
  }else{
     DictionaryIterator *iter;
     app_message_outbox_begin(&iter);
     int key = 0;
    // send key 0 :value convert to the phone
     Tuplet value = TupletCString(key, "convert");
     dict_write_tuplet(iter, &value);
    // clear display layer
     text_layer_set_text(hello_layer, "");
     app_message_outbox_send();
     layer_set_hidden	((Layer*)hello_layer, false);
  }		
}

/* Call back function for menu items pause*/
static void pause_handler(int index, void* not_used){
  if(! layer_get_hidden((Layer*)hello_layer)){
     layer_set_hidden	((Layer*)hello_layer, true); 
  }else{
     DictionaryIterator *iter;
     app_message_outbox_begin(&iter);
     int key = 0;
    // send key 0 :value pause to the phone
     Tuplet value = TupletCString(key, "pause");
     dict_write_tuplet(iter, &value);
    // clear display layer
     text_layer_set_text(hello_layer, "");
     app_message_outbox_send();
     layer_set_hidden	((Layer*)hello_layer, false);
  }
}

/* Call back function for menu items restart*/
static void restart_handler(int index, void* not_used){
  if(! layer_get_hidden((Layer*)hello_layer)){
     layer_set_hidden	((Layer*)hello_layer, true); 
  }else{
    DictionaryIterator *iter;
    app_message_outbox_begin(&iter);
    int key = 0;
    // send key 0 :value resume to the phone
    Tuplet value = TupletCString(key, "resume");
    dict_write_tuplet(iter, &value);
    //clear display layer
    text_layer_set_text(hello_layer, "");
    app_message_outbox_send();
    layer_set_hidden	((Layer*)hello_layer, false);
  }
}

/* Initial simple menu section*/ 
static void init_section(){
  section = (SimpleMenuSection*) malloc(sizeof(SimpleMenuSection));
  section-> num_items = 8;
  section-> title = NULL;
  SimpleMenuItem *array = malloc(sizeof(SimpleMenuItem) * 8);
  int i = 0;
  for(i = 0; i < 8; ++i){
      array[i].icon = NULL;
      array[i].subtitle = NULL; 
      if(i == 0){
        array[i].callback = &show_handler;
        array[i].title = "Show current T";
      }else if(i == 1){
        array[i].title = "Convert (C/F)";
        array[i].callback = &convert_handler;
      }else if(i == 2){
        array[i].title = "Pause";
        array[i].callback = &pause_handler;
      }else if(i == 3){
        array[i].title = "Resume";
        array[i].callback = &restart_handler;
      }else if(i == 4){
        array[i].title = "Data";
        array[i].callback = &data_handler;
      }else if(i == 5){
        array[i].title = "Running time";
        array[i].callback = &time_handler;
      }else if(i == 6){
        array[i].title = "Trends";
        array[i].callback = &trend_handler;
      }else{
        array[i].title = "Help !!!";
        array[i].callback = &help_handler;
      }
  }
  section -> items = array;
}

/*Initial window display*/
static void window_load(Window *window) {
  Layer *window_layer = window_get_root_layer(window);
  GRect bounds = layer_get_bounds(window_layer);

  init_section();
  hello_layer = text_layer_create((GRect) { .origin = { 0, 0 }, .size = { bounds.size.w, bounds.size.h } });
  menu_layer = simple_menu_layer_create	((GRect) { .origin = { 0, 0 }, .size = { bounds.size.w, bounds.size.h } },
  window, 
  section,
  1,
    NULL 
  );	  
  text_layer_set_text(hello_layer, "SELECT to show temperature");
  text_layer_set_text_alignment(hello_layer, GTextAlignmentCenter);
  text_layer_set_font(hello_layer, fonts_get_system_font(FONT_KEY_ROBOTO_CONDENSED_21))	;
  text_layer_set_background_color	(	hello_layer,GColorBlack );
  text_layer_set_text_color	(	hello_layer,GColorWhite);	
  layer_add_child(window_layer,(Layer*) menu_layer);
  layer_add_child((Layer*) menu_layer,(Layer*) hello_layer);
  layer_set_hidden((Layer*)hello_layer, true);		
}

/*Destroy window display*/
static void window_unload(Window *window) {
  simple_menu_layer_destroy	(menu_layer);	
  text_layer_destroy(hello_layer);
}


void out_sent_handler(DictionaryIterator *sent, void *context) {
   // outgoing message was delivered
   text_layer_set_text(hello_layer, "Out sent!");
 }


void out_failed_handler(DictionaryIterator *failed, AppMessageResult reason, void *context) {
  // outgoing message failed
  text_layer_set_text(hello_layer, "Error out!");
}


void in_received_handler(DictionaryIterator *received, void *context) {
  // incoming message received
  text_layer_set_text(hello_layer, "Message received!");
  // looks for key #0 to #3 in the incoming message
   int key = 0;
   Tuple *text_tuple;
   for(key = 0; key < 4; ++key){
       text_tuple = dict_find(received, key);  
       if(text_tuple) break;
   }
   if (text_tuple) {
     if (text_tuple->value) {
       // put it in this global variable
       strcpy(msg, text_tuple->value->cstring);
     }else {
       strcpy(msg, "No value from phone!");
     }
     text_layer_set_text(hello_layer, msg);
     		
   }
   else {
     text_layer_set_text(hello_layer, "No message from phone!");
   }
     
}

void in_dropped_handler(AppMessageResult reason, void *context) {
  // incoming message dropped
  text_layer_set_text(hello_layer, "Error in!");
}

static void init(void) {
  window = window_create();
  window_set_window_handlers(window, (WindowHandlers) {
    .load = window_load,
    .unload = window_unload,
  });

  // for registering AppMessage handlers
  app_message_register_inbox_received(in_received_handler);
  app_message_register_inbox_dropped(in_dropped_handler);
  app_message_register_outbox_sent(out_sent_handler);
  app_message_register_outbox_failed(out_failed_handler);
  const uint32_t inbound_size = 64;
  const uint32_t outbound_size = 64;
  app_message_open(inbound_size, outbound_size);
  
  const bool animated = true;
  window_stack_push(window, animated);
}

static void deinit(void) {
  window_destroy(window);
}

int main(void) {
  init();
  app_event_loop();
  deinit();
}