const app = Vue.
   createApp({
      data() {
         return {
            selectedTypeLoan: 0,
            selectedPayments: null,
            LoanDestination: null,
            LoanAmount: 0,
            unfilteredAccounts: [],
            accounts: [],
            
            loans: [],
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

         axios.get("/api/loans")
            .then(response => {
               this.loans = response.data
               console.log(this.loans)
            })
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
         createLoan() {
            Swal.fire({
               title: 'Are you sure?',
               text: "Do you want to get a loan?",
               icon: 'warning',
               showCancelButton: true,
               confirmButtonColor: '#3085d6',
               cancelButtonColor: '#d33',
               confirmButtonText: "Yes, I'm sure!",
               showCloseButton: true,
            }).then((result) => {
               if (result.isConfirmed) {
                  axios.post('/api/client/loans',{
                     'id' : `${this.selectedTypeLoan}`,
                     'amount' : `${this.LoanAmount}`,
                     'payments' : `${this.selectedPayments}`,
                     'accountDestination' : `${this.LoanDestination}`
                     })
                     .then(response => {
                        Swal.fire({
                           position: 'top-end',
                           icon: 'success',
                           title: 'Successful loan',
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
         plus() {
            return this.LoanAmount * 1.20
         },
         formattedNumber(balance) {
            return balance = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(balance)
         },
      },
      computhed:{
         
      }
   }).mount('#app');