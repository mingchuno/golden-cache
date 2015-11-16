'use strict';

/**
 * The application.
 */
var app = angular.module('hkgApp', [
  'ui.router',
  'ngSanitize'
]);

/**
 * The application routing.
 */
app.config(function ($urlRouterProvider, $stateProvider, $httpProvider) {

  $urlRouterProvider.otherwise('/topics/1');

  $stateProvider
    .state('topics', { url: '/topics/:page', templateUrl: '/assets/views/home.html',controller: 'HomeCtrl'})
    .state('post', { url: '/post/:messageId/:page', templateUrl: '/assets/views/post.html',  controller: 'PostCtrl', controllerAs: 'vm'});
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
        var maxPage = Math.min(9, parseInt(obj.totalReplies / 25)) + 1;
        console.log("----max page is-----");
        console.log(maxPage);
        var pageArray = Array.apply(null, Array(maxPage)).map(function (_, i) {return i+1;});
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
    console.log(response);
    vm.post = response;
  });
}]);