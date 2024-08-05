async function theModal(form, modal, id){
    modal.show();
    let user = await getUserId(id);
    form.id.value = user.id;
    form.username.value = user.username;
    form.surname.value = user.surname;
    form.age.value = user.age;
    form.password.value = user.password;
    form.roles.value = user.roles;
}