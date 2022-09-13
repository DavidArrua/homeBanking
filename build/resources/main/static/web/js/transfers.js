const app = Vue.
   createApp({
      data() {
         return {
            accounts: [],
            unfilteredAccounts: [],
            selectedAccountOrigin: '',
            selectedAccountDestiny: '',
            description: '',
            amount: 0,
            transferDestination: 'own',
            togleO: true,
            togleT: false


         }
      },
      created() {
         axios.get("/api/clients/current")
            .then(response => {
               this.unfilteredAccounts = response.data.accounts
               this.accounts = this.unfilteredAccounts.filter(account => account.accountState == true)
            })
            .catch(function (error) {
               console.log(error);
            });
      },
      methods: {
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
         sendTransfer() {
            Swal.fire({
               title: 'Are you sure?',
               text: "Do you want to make the transfer?",
               icon: 'warning',
               showCancelButton: true,
               confirmButtonColor: '#3085d6',
               cancelButtonColor: '#d33',
               confirmButtonText: "Yes, I'm sure!",
               showCloseButton: true,
            }).then((result) => {
               if (result.isConfirmed) {
                  axios.post("/api/clients/current/transaction",`amount=${this.amount}&description=${this.description}&originAccount=${this.selectedAccountOrigin}&destinyAccount=${this.selectedAccountDestiny}`,
                  {headers:{'content-type':'application/x-www-form-urlencoded'}})
                  .then(response => {
                        Swal.fire({
                           position: 'top-end',
                           icon: 'success',
                           title: 'Successful transfer',
                           showConfirmButton: false,
                           timer: 2500,
                           timerProgressBar: true,
                           toast: true,
                        })
                     })
                     .then(() => new Promise(resolve => setTimeout(resolve, 2500)))
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
            })
         },
         changeTransferOwner() {
            this.transferDestination = 'own'
            this.togleO = !this.togleO
            this.togleT = false
         },
         changeTransferThird() {
            this.transferDestination = 'third'
            this.togleT = !this.togleT
            this.togleO = false
         },
         emptyData(){
            Swal.fire({
               position: 'top-end',
               icon: 'error',
               title: 'Missing data',
               showConfirmButton: false,
               timer: 2500,
               timerProgressBar: true,
               toast: true,
               showCloseButton: true,
            })
         },
      },
   }).mount('#app');
