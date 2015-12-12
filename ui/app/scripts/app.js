'use strict';

moment.locale('zh-tw');

/**
 * The application.
 */
var app = angular.module('hkgApp', [
  'ui.router',
  'ngSanitize',
  'ngResource',
  'angular-loading-bar',
  'ui.bootstrap'
]);

/**
 * The application routing.
 */
app.config(['$urlRouterProvider', '$stateProvider', '$httpProvider', function ($urlRouterProvider, $stateProvider, $httpProvider) {

  $urlRouterProvider.otherwise('/topics/BW/1');

  $httpProvider.useLegacyPromiseExtensions(false);

  $stateProvider

    .state('topics', { 
      url: '/topics/:channel/:page', 
      templateUrl: '/views/topics.html', 
      controller: 'TopicsCtrl',
      params: {
        page: "1"
      }
    })

    .state('notFound', { 
      url: '/404', 
      templateUrl: '/404.html'
    })

    .state('post', {
      url: '/post/:messageId/:page', 
      templateUrl: '/views/post.html',  
      controller: 'PostCtrl', 
      controllerAs: 'vm',
      params: {
        page: "1",
        lastChannel: "BW"
      }
    });
}]);

app.factory("TitleService", function() {
  return {
    getDefaultTitle: function() {
      return ' - HKG Cache v1.2.7 [Beta]';
    }
  }
});

app.factory("HistoryService", function($resource) {
  return $resource("/api/v1/history");
});

app.factory("ChannelService", function() {
  var channels = [
    {c: "ET",d: "娛樂台"},
    {c: "CA",d: "時事台"},
    {c: "FN",d: "財經台"},
    {c: "GM",d: "遊戲台"},
    {c: "HW",d: "硬件台"},
    {c: "IN",d: "電訊台"},
    {c: "SW",d: "軟件台"},
    {c: "MP",d: "手機台"},
    {c: "AP",d: "Apps台"},
    {c: "SP",d: "體育台"},
    {c: "LV",d: "感情台"},
    {c: "SY",d: "講故台"},
    {c: "ED",d: "飲食台"},
    {c: "PT",d: "寵物台"},
    {c: "BB",d: "親子台"},
    {c: "TR",d: "旅遊台"},
    {c: "CO",d: "潮流台"},
    {c: "AN",d: "動漫台"},
    {c: "TO",d: "玩具台"},
    {c: "MU",d: "音樂台"},
    {c: "VI",d: "影視台"},
    {c: "DC",d: "攝影台"},
    {c: "ST",d: "學術台"},
    {c: "WK",d: "上班台"},
    {c: "TS",d: "汽車台"},
    {c: "RA",d: "電　台"},
    {c: "AU",d: "成人台"},
    {c: "MB",d: "站務台"},
    {c: "AC",d: "活動台"},
    {c: "JT",d: "直播台"},
    {c: "EP",d: "創意台"},
    {c: "BW",d: "吹水台"}
  ];

  return {
    getChannel: function() {
      return channels;
    },
    findCurrentChannelDisplayName: function(codeName) {
      return channels.filter(function(a){ return a.c == codeName})[0].d;
    }
  };
});

/**
 * The home controller.
 */
app.controller('TopicsCtrl', [
  '$http', 
  '$scope', 
  '$state', 
  '$window', 
  'ChannelService', 
  'TitleService',
  'HistoryService',
  function($http, $scope, $state, $window, ChannelService, TitleService, HistoryService) {

  $scope.vm = this;
  var vm = $scope.vm;

  $scope.channels = ChannelService.getChannel();

  $scope.channelDisplayName = ChannelService.findCurrentChannelDisplayName($state.params.channel);

  $scope.channelCodeName = $state.params.channel;

  $scope.hideReplyTime = $($window).width() < 1000;

  $scope.hideAuthor = $($window).width() < 640;

  vm.currPage = $state.params.page;

  $scope.pageChanged = function() {
    // console.log('Page changed to: ' + vm.currPage);
    $state.go('topics', {channel: $scope.channelCodeName, page: vm.currPage});
  };

  // hard code now
  $window.document.title = $scope.channelDisplayName + TitleService.getDefaultTitle();

  HistoryService.query(function(data) {
    vm.history = data;
  });

  vm.purgeHistory = function() {
    vm.history = [];
    HistoryService.delete();
  };

  $http({
    url: "/api/v1/topics",
    method: 'GET',
    params: {
      page: $state.params.page,
      channel: $state.params.channel
    }
  }).
  then(function(response) {
    var reformattedObject = response.data.topicList.map(function(obj){
        // each topic
        // var maxPage = Math.min(9, parseInt(obj.totalReplies / 25)) + 1;
        var maxPage = parseInt(obj.totalReplies / 25) + 1;
        // console.log("----max page is-----");
        // console.log(maxPage);
        if (maxPage <= 12) {
          var pageArray = Array.apply(null, Array(maxPage)).map(function (_, i) {return i+1;});
        } else {
          var pageArray = [1,2,3,4,5,6].concat(Array.apply(null, Array(6)).map(function (_, i) {return maxPage - i;}).reverse());
        }
        obj.pages = pageArray;

        // use moment to parse and display time
        obj.lastReplyDateString = moment(obj.lastReplyDate).fromNow();

        if (obj.rating > 0) {
          obj.color = 'green';
        }

        if (obj.rating < 0) {
          obj.color = 'red';
        }

        if (obj.rating == 0) {
          obj.color = '#333';
        }

        return obj;
    });

    $scope.topics = reformattedObject;
  }, function(response) {
      $state.go('notFound')
  });
}]);

/**
 * The post controller
 */
app.controller("PostCtrl", [
  '$scope', 
  '$http', 
  '$state', 
  '$window', 
  'TitleService', 
  'ChannelService', 
  function($scope, $http, $state, $window, TitleService, ChannelService) {

  $scope.vm = this;
  var vm = $scope.vm;

  vm.lastChannel = $state.params.lastChannel;

  vm.channelDisplayName = ChannelService.findCurrentChannelDisplayName($state.params.lastChannel);

  $scope.pageChanged = function() {
    console.log('Page changed to: ' + vm.post.currentPages);
    $state.go('post', {messageId: vm.post.messageId, page: vm.post.currentPages});
  };

  $http({
    url: "/api/v1/post",
    method: 'GET',
    params: {
      messageId: $state.params.messageId,
      page: $state.params.page
    }
  }).
  then(function(response){
    response.data.messages = response.data.messages.map(function(obj){
      // date part
      // var d = new Date(obj.messageDate);
      // var dStr = d.toLocaleDateString() + ' ' + d.toLocaleTimeString();
      // obj.replyDateStr = dStr;

      // use moment to parse and display time
      obj.replyDateStr = moment(obj.messageDate).format('YYYY-MM-DD HH:mm:ss');

      return obj;
    });

    $window.document.title = response.data.messageTitle + TitleService.getDefaultTitle();
    vm.post = response.data;
  

    $scope.pageValue = vm.post.currentPages; 
    $scope.pageChange = function(messageId, pageValue){
     $state.go('post', {messageId: messageId, page: pageValue})
    }
  
  }, function(response){
    $state.go('notFound')
  });
}]);