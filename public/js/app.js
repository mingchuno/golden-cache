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
    .state('test', { url: '/post/:messageId/:page', templateUrl: '/assets/views/post.html',  controller: 'PostCtrl', controllerAs: 'vm'});
});

/**
 * The home controller.
 */
app.controller('HomeCtrl', ['$http', '$scope', '$state', function($http, $scope, $state) {

  // $http.get("/api/v1/topics")
  // .success(function(response) {
  //   $scope.topics = response.topicList;
  // });
  $http({
    url: "/api/v1/topics",
    method: 'GET',
    params: {
      page: $state.params.page
    }
  })
  .success(function(response) {
    $scope.topics = response.topicList;
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
  .success(function(response) {vm.post = response;});
}]);