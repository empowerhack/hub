(function() {
    'use strict';

    angular
        .module('hubApp')
        .controller('SkillDialogController', SkillDialogController);

    SkillDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Skill', 'User'];

    function SkillDialogController ($scope, $stateParams, $uibModalInstance, entity, Skill, User) {
        var vm = this;
        vm.skill = entity;
        vm.users = User.query();
        vm.load = function(id) {
            Skill.get({id : id}, function(result) {
                vm.skill = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hubApp:skillUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.skill.id !== null) {
                Skill.update(vm.skill, onSaveSuccess, onSaveError);
            } else {
                Skill.save(vm.skill, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
