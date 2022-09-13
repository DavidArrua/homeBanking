const app = Vue.
   createApp({
      data() {
         return {
            selectedType: null,
            selectedColor: null,
            selectedAccount: null,
            unfilteredAccounts: [],
            accounts: [],
         }
      },
      created() {
         axios.get("/api/clients/current")
            .then(response => {
               this.unfilteredAccounts =response.data.accounts
               this.accounts = this.unfilteredAccounts.filter(account => account.accountState == true)
            })
            .catch(function (error) {
               console.log(error);
            });
      },
      methods: {
         createCards() {
            Swal.fire({
               title: 'Are you sure?',
               text: "Do you want to create a new card?",
               icon: 'warning',
               showCancelButton: true,
               confirmButtonColor: '#3085d6',
               cancelButtonColor: '#d33',
               confirmButtonText: "Yes, I'm sure!",
               showCloseButton: true,
            }).then((result) => {
               if (result.isConfirmed) {
                  axios.post('/api/clients/current/cards', `cardType=${this.selectedType}&cardColor=${this.selectedColor}&numberAccount=${this.selectedAccount}`)
                  .then(response => {
                     Swal.fire({
                        position: 'top-end',
                        icon: 'success',
                        title: 'Card created successfully',
                        showConfirmButton: false,
                        timer: 2500,
                        timerProgressBar: true,
                        toast: true,
                     })
                  })
                  .then(() => new Promise(resolve => setTimeout(resolve, 2500)))
                  .then(response => window.location.href = '/web/cards.html')
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
            })
         },
         logOut() {
            Swal.fire({
               title: 'Are you sure?',
               text: "Do you want to exit the app?",
               icon: 'warning',
               showCancelButton: true,
               confirmButtonColor: '#3085d6',
               cancelButtonColor: '#d33',
               confirmButtonText: "Yes, I'm sure!",
               showCloseButton: true,
            }).then((result) => {
               if (result.isConfirmed) {
                  axios.post('/api/logout')
                  .then(response => location.href = '/web/index.html')
                     .then(response => location.href = '/web/index.html')
                     .catch(function (error) {
                        alert(error);
                     })
               }
            })
         },
      },
   }).mount('#app');