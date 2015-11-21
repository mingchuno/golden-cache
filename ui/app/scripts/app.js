'use strict';

/**
 * The application.
 */
var app = angular.module('hkgApp', [
  'ui.router',
  'ngSanitize',
  'angular-loading-bar'
]);

/**
 * The application routing.
 */
app.config(['$urlRouterProvider', '$stateProvider', '$httpProvider', function ($urlRouterProvider, $stateProvider, $httpProvider) {

  $urlRouterProvider.otherwise('/topics/1');

  $httpProvider.useLegacyPromiseExtensions(false);

  $stateProvider
    .state('topics', { url: '/topics/:page', templateUrl: '/views/topics.html',controller: 'TopicsCtrl'})
    .state('notFound', { url: '/404', templateUrl: '/404.html'})
    .state('post', { url: '/post/:messageId/:page', templateUrl: '/views/post.html',  controller: 'PostCtrl', controllerAs: 'vm'});
}]); 

/**
 * The home controller.
 */
app.controller('TopicsCtrl', ['$http', '$scope', '$state', '$window', function($http, $scope, $state, $window) {

  $http({
    url: "/api/v1/topics",
    method: 'GET',
    params: {
      page: $state.params.page
    }
  }).
  then(function(response){
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

    // hard code now
    $window.document.title = '吹水台 - HKG Cache v1.2.2 [Beta]';

    $scope.topics = reformattedObject;
    $scope.nextPage = parseInt($state.params.page) + 1;
    $scope.prevPage = parseInt($state.params.page) - 1;
    $scope.hidePrev = $state.params.page == 1;
  }, function(response) {
      $state.go('notFound')
  });
}]);

/**
 * The post controller
 */
app.controller("PostCtrl", ['$scope', '$http', '$state', '$window', function($scope, $http, $state, $window) {
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

    $window.document.title = response.data.messageTitle + ' - HKG Cache v1.2.2 [Beta]';

    vm.post = response.data;
  }, function(response){
    $state.go('notFound')
  });
}]);