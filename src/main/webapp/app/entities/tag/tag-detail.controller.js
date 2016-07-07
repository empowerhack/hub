(function() {
    'use strict';

    angular
        .module('hubApp')
        .controller('TagDetailController', TagDetailController);

    TagDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Tag', 'Project'];

    function TagDetailController($scope, $rootScope, $stateParams, entity, Tag, Project) {
        var vm = this;

        vm.tag = entity;

        var unsubscribe = $rootScope.$on('hubApp:tagUpdate', function(event, result) {
            vm.tag = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
