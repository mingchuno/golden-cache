'use strict';

describe('Controller: TopicsCtrl', function () {

  // load the controller's module
  beforeEach(module('hkgApp'));

  var MainCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    MainCtrl = $controller('TopicsCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  /* Comment out first for testing right now 
  it('should attach a list of awesomeThings to the scope', function () {
     expect(MainCtrl.awesomeThings.length).toBe(3);
  }); */
  
});
