'use strict';

describe('Controller: TopicsCtrl', function () {

  // load the controller's module
  beforeEach(module('hkgApp'));

  var TopicsCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    var noOp = function noOp() {};
    scope = $rootScope.$new();
    TopicsCtrl = $controller('TopicsCtrl', {
      $http: function() { return {then: noOp}; },
      $scope: scope,
      $state: {
        params: {}
      },
      $window: window,
      ChannelService: {
        getChannel: noOp,
        findCurrentChannelDisplayName: noOp
      },
      TitleService: {
        getDefaultTitle: noOp
      },
      HistoryService: {
        query: noOp
      }
    });
  }));

  it('should initialize', function() {
    expect(TopicsCtrl).toBeDefined();
  })
});
