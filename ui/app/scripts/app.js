'use strict';

/**
 * The application.
 */
var app = angular.module('hkgApp', [
  'ui.router',
  'ngSanitize',
  // 'ngResource',
  'angular-loading-bar'
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
        page: "1"
      }
    });
}]);

app.factory("TitleService", function() {
  return {
    getDefaultTitle: function() {
      return ' - HKG Cache v1.2.2 [Beta]';
    }
  }
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
app.controller('TopicsCtrl', ['$http', '$scope', '$state', '$window', 'ChannelService', 'TitleService', function($http, $scope, $state, $window, ChannelService, TitleService) {
  $scope.channels = ChannelService.getChannel();

  $scope.channelNow = ChannelService.findCurrentChannelDisplayName($state.params.channel)

  // hard code now
  $window.document.title = $scope.channelNow + TitleService.getDefaultTitle();

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

        // date part
        var d = new Date(obj.lastReplyDate);
        var dStr = d.toLocaleDateString() + ' ' + d.toLocaleTimeString();
        obj.lastReplyDateString = dStr;

        return obj;
    });

    $scope.topics = reformattedObject;
    $scope.nextPage = 1 + $state.params.page;
    $scope.prevPage = 1 - $state.params.page;
    $scope.hidePrev = $state.params.page == 1;
  }, function(response) {
      $state.go('notFound')
  });
}]);

/**
 * The post controller
 */
app.controller("PostCtrl", ['$scope', '$http', '$state', '$window', 'TitleService', function($scope, $http, $state, $window, TitleService) {
  $scope.vm = this;
  var vm = $scope.vm;

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
      var d = new Date(obj.messageDate);
      var dStr = d.toLocaleDateString() + ' ' + d.toLocaleTimeString();
      obj.replyDateStr = dStr;

      return obj;
    });

    $window.document.title = response.data.messageTitle + TitleService.getDefaultTitle();

    vm.post = response.data;
  }, function(response){
    $state.go('notFound')
  });
}]);