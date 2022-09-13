const app = Vue.
   createApp({
      data() {
         return {
            password: "",
            email: "",
            firstNameRegister: "",
            lastNameRegister: "",
            emailRegister: "",
            passwordRegister: "",
         }
      },
      created() {

      },
      methods: {
         signIn() {
            axios.post("/api/login", `email=${this.email}&pwd=${this.password}`,
               { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
               .then(response => location.href = '/web/accounts.html')
               .catch(error => {
                  Swal.fire({
                     position: 'top-end',
                     icon: 'error',
                     title: error.response.data,
                     showConfirmButton: false,
                     timer: 2500,
                     timerProgressBar: true,
                     toast: true,
                  });
               })
         },
         signUp() {
            axios.post('/api/clients', `firstName=${this.firstNameRegister}&lastName=${this.lastNameRegister}&email=${this.emailRegister}&password=${this.passwordRegister}`)
               .then(response => console.log('registered'))
               .then(response => axios.post("/api/login", `email=${this.emailRegister}&pwd=${this.passwordRegister}`))
               .then(response => location.href = '/web/accounts.html')
               .catch(error => {
                  Swal.fire({
                     position: 'top-end',
                     icon: 'error',
                     title: error.response.data,
                     showConfirmButton: false,
                     timer: 2500,
                     timerProgressBar: true,
                     toast: true,
                  });
               })
         }
      },
   }).mount('#app');