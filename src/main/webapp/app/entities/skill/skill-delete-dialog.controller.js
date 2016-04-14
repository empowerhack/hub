(function() {
    'use strict';

    angular
        .module('hubApp')
        .controller('SkillDeleteController',SkillDeleteController);

    SkillDeleteController.$inject = ['$uibModalInstance', 'entity', 'Skill'];

    function SkillDeleteController($uibModalInstance, entity, Skill) {
        var vm = this;
        vm.skill = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Skill.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
