/**
 * Copyright 2015 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* global $:true */

'use strict';

// conversation variables

var conversation_id, client_id, speakText = "";

$(document).ready(function () {
  var $chatInput = $('.chat-window--message-input'),
    $jsonPanel = $('#json-panel .base--textarea'),
    $information = $('.data--information'),
    $profile = $('.data--profile'),
    $loading = $('.loader');


  $chatInput.keyup(function(event){
    if(event.keyCode === 13) {
      converse($(this).val());
    }
  });

  var converse = function(userText) {
    $loading.show();
    // $chatInput.hide();

    // check if the user typed text or not
    if (typeof(userText) !== undefined && $.trim(userText) !== '')
      submitMessage(userText);

    // build the conversation parameters
    var params = { input : userText };
    params.action="conversation";

    // check if there is a conversation in place and continue that
    // by specifing the conversation_id and client_id
    if (conversation_id) {
      params.conversationId = conversation_id;
      params.clientId = client_id;
    }

    $.post('mvc/dialog/conversation', params)
      .done(function onSucess(dialog) {
	    //dialog = JSON.parse(dialog);

        $chatInput.val(''); // clear the text input

        $jsonPanel.html(JSON.stringify(dialog, null, 2));

        // update conversation variables
        conversation_id = dialog.conversationId;
        client_id = dialog.clientId;

        var response = dialog.content;
        var orderText = dialog.orderText;

        $chatInput.show();
        $chatInput[0].focus();

        $information.empty();

        addProperty($information, 'Conversation ID: ', conversation_id);
        addProperty($information, 'Client ID: ', client_id);

        talk('WATSON', response); // show
        speakText = response.replace('&lt;br/&gt;', '');
        
        $("#orderText").html(orderText);
        
        //getProfile();
      })
      .fail(function onError(error) {
        talk('WATSON', error.responseJSON ? error.responseJSON.error : error.statusText);
      })
      .always(function always(){
        $loading.hide();
        scrollChatToBottom();
        $chatInput.focus();
      });

  };

  var getProfile = function() {
    var params = {
      conversation_id: conversation_id,
      client_id: client_id
    };

    $.post('mvc/dialog/profile', params).done(function(data) {
      data = JSON.parse(data);
      $profile.empty();
      data.name_values.forEach(function(par) {
        if (par.value !== '')
          addProperty($profile, par.name + ':', par.value);
      });
    })
    .fail(function onError(error) {
    	talk('WATSON', error.responseJSON ? error.responseJSON.error : error.statusText);
    });
  };

  var scrollChatToBottom = function() {
    var element = $('.chat-box--pane');
    element.animate({
      scrollTop: element[0].scrollHeight
    }, 420);
  };

  var scrollToInput = function() {
      var element = $('.chat-window--message-input');
      $('body, html').animate({
        scrollTop: (element.offset().top - window.innerHeight + element[0].offsetHeight) + 20 + 'px'
      });
  };

  var talk = function(origin, text) {
    var $chatBox = $('.chat-box--item_' + origin).first().clone();
    var $loading = $('.loader');
    $chatBox.find('p').html($('<p/>').html(text).text());
    // $('.chat-box--pane').append($chatBox);
    $chatBox.insertBefore($loading);
    setTimeout(function() {
      $chatBox.removeClass('chat-box--item_HIDDEN');
    }, 100);
  };

  var addProperty = function($parent, name, value) {
    var $property = $('.data--variable').last().clone();
    $property.find('.data--variable-title').text(name);
    $property.find('.data--variable-value').text(value);
    $property.appendTo($parent);
    setTimeout(function() {
      $property.removeClass('hidden');
    }, 100);
  };

  var submitMessage = function(text) {
    talk('YOU', text);
    scrollChatToBottom();
    clearInput();
  };

  var clearInput = function() {
    $('.chat-window--message-input').val('');
  };

  $('.tab-panels--tab').click(function(e){
    e.preventDefault();
    var self = $(this);
    var inputGroup = self.closest('.tab-panels');
    var idName = null;

    inputGroup.find('.active').removeClass('active');
    self.addClass('active');
    idName = self.attr('href');
    $(idName).addClass('active');
  });

  // Initialize the conversation
  converse();
  scrollToInput();

});

var updateDialog = function(){
	$("#btnDialog").attr("disabled", true);
	$("#updateLoading").show();
	$.get('demo/update?action=update').done(function(data) {
		$("#updateLoading").hide();
		$("#btnDialog").removeAttr("disabled");
		alert(data);
    })
    .fail(function onError(error) {
    	$("#updateLoading").hide();
    	$("#btnDialog").removeAttr("disabled");
    	talk('WATSON', error.responseJSON ? error.responseJSON.error : error.statusText);
    });
}

var speak = function(){
	$("#btnSpeak").attr("disabled", true);
	$("#updateLoading").show();
	var params = { text : speakText };
	$.post('demo/speak?action=speak', params).done(function(data) {
		var url = host + data;
		$("#btnSpeak").removeAttr("disabled");
		$("#updateLoading").hide();
		$("#speakDiv").empty();
		$("#speakDiv").append("<embed src=\"" + url + "\">");
    })
    .fail(function onError(error) {
    	$("#btnSpeak").removeAttr("disabled");
    	$("#updateLoading").hide();
    	alert("speak fail");
    });
}