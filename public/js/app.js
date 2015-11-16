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

  $stateProvider
    .state('topics', { url: '/topics/:page', templateUrl: '/assets/views/home.html',controller: 'HomeCtrl'})
    .state('post', { url: '/post/:messageId/:page', templateUrl: '/assets/views/post.html',  controller: 'PostCtrl', controllerAs: 'vm'});
}]); 

app.filter('num', function() {
    return function(input) {
      return parseInt(input, 10);
    };
});

/**
 * The home controller.
 */
app.controller('HomeCtrl', ['$http', '$scope', '$state', function($http, $scope, $state) {
  $http({
    url: "/api/v1/topics",
    method: 'GET',
    params: {
      page: $state.params.page
    }
  }).success(function(response) {
    var reformattedObject = response.topicList.map(function(obj){ 
        // each topic
        // var maxPage = Math.min(9, parseInt(obj.totalReplies / 25)) + 1;
        var maxPage = parseInt(obj.totalReplies / 25) + 1;
        console.log("----max page is-----");
        console.log(maxPage);
        if (maxPage <= 12) {
          var pageArray = Array.apply(null, Array(maxPage)).map(function (_, i) {return i+1;});
        } else {
          var pageArray = [1,2,3,4,5,6].concat(Array.apply(null, Array(6)).map(function (_, i) {return maxPage - i;}).reverse());
        }
        // var pageArray = Array.apply(null, Array(maxPage)).map(function (_, i) {return i+1;});
        obj['pages'] = pageArray;

        // date part
        var d = new Date(obj.lastReplyDate);
        var dStr = d.toLocaleDateString() + ' ' + d.toLocaleTimeString();
        obj['lastReplyDateString'] = dStr;

        return obj;
    });

    $scope.topics = reformattedObject;
    $scope.nextPage = parseInt($state.params.page) + 1;
    $scope.prevPage = parseInt($state.params.page) - 1;
    $scope.hidePrev = $state.params.page == 1;

  });

}]);

/**
 * The post controller
 */
app.controller("PostCtrl", ['$scope', '$http', '$state', function($scope, $http, $state) {
  $scope.vm = this;
  var vm = $scope.vm;

  $http({
    url: "/api/v1/post",
    method: 'GET',
    params: {
      messageId: $state.params.messageId,
      page: $state.params.page
    }
  })
  .success(function(response) {
    response['messages'] = response.messages.map(function(obj){
      // date part
      var d = new Date(obj.messageDate);
      var dStr = d.toLocaleDateString() + ' ' + d.toLocaleTimeString();
      obj['replyDateStr'] = dStr;

      return obj;
    });

    vm.post = response;
  });
}]);