const app = Vue.
   createApp({
      data() {
         return {
            clients: [],
            unfilteredAccounts: [],
            accounts: [],
            loans: [],
            accountType: 'CURRENT',
            accountDelete: '',
            togleHiden: true,
            togleS : false,
            togleC : false,
         }
      },
      created() {
         this.getData();
      },
      methods: {
         getData(){
            axios.get("/api/clients/current")
            .then(response => {
               this.clients = response.data;
               this.unfilteredAccounts =this.clients.accounts
               this.accounts = this.unfilteredAccounts.filter(account => account.accountState == true)
               this.loans = this.clients.loans;
            })
            .catch(function (error) {
               console.log(error);
            });
         },
         newDate(creationDate) {
            return creationDate = new Date(creationDate).toLocaleDateString()
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
         formattedNumber(balance) {
            return balance = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(balance)
         },
         CreateAccount() {
            Swal.fire({
               title: 'Are you sure?',
               text: "Do you want to create an account?",
               icon: 'warning',
               showCancelButton: true,
               confirmButtonColor: '#3085d6',
               cancelButtonColor: '#d33',
               confirmButtonText: "Yes, I'm sure!",
               showCloseButton: true,
            }).then((result) => {
               if (result.isConfirmed) {
                  axios.post('/api/clients/current/accounts',`accountType=${this.accountType}`)
                  .then(response => {
                     Swal.fire({
                        position: 'top-end',
                        icon: 'success',
                        title: 'Account Created successfully',
                        showConfirmButton: false,
                        timer: 2500,
                        timerProgressBar: true,
                        toast: true,
                     })
                  })
                  .then(response => this.getData())
                  
               }
            })
         },
         changeTypeAccountCurrent(){
            this.accountType = 'CURRENT';
            this.togleC = !this.togleC
            this.togleS = false
         },
         changeTypeAccountSaving(){
            this.accountType = 'SAVING';
            this.togleS = !this.togleS
            this.togleC = false
         },
         deleteAccount(){
            Swal.fire({
               title: 'Are you sure?',
               text: "Do you want to delete an account?",
               icon: 'warning',
               showCancelButton: true,
               confirmButtonColor: '#3085d6',
               cancelButtonColor: '#d33',
               confirmButtonText: "Yes, I'm sure!",
               showCloseButton: true,
            }).then((result) => {
               if (result.isConfirmed) {
                  axios.patch('/api/clients/current/accounts/state',`number=${this.accountDelete}`)
                  .then(response => {
                     Swal.fire({
                        position: 'top-end',
                        icon: 'success',
                        title: 'Account deleted successfully',
                        showConfirmButton: false,
                        timer: 2500,
                        timerProgressBar: true,
                        toast: true,
                     })
                  })
                  .then(response => this.getData())
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
         togleHIdenButton(){
            this.togleHiden = !this.togleHiden
         },
         showMoney(balance){
            if(this.togleHiden == false){
               replace = balance.splice(0,10000000)
               return replace.replace('*** ***')
            }else{
               return balance
            }
         },
         
      },
   }).mount('#app');
