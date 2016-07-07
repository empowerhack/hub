(function() {
    'use strict';

    angular
        .module('hubApp')
        .controller('ProjectDetailController', ProjectDetailController);

    ProjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Project', 'Partner', 'Tag'];

    function ProjectDetailController($scope, $rootScope, $stateParams, entity, Project, Partner, Tag) {
        var vm = this;

        vm.project = entity;

        var unsubscribe = $rootScope.$on('hubApp:projectUpdate', function(event, result) {
            vm.project = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
