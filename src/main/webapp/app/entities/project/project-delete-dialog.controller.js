(function() {
    'use strict';

    angular
        .module('hubApp')
        .controller('ProjectDeleteController',ProjectDeleteController);

    ProjectDeleteController.$inject = ['$uibModalInstance', 'entity', 'Project'];

    function ProjectDeleteController($uibModalInstance, entity, Project) {
        var vm = this;

        vm.project = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Project.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
