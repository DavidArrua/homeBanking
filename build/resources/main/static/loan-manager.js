const app = Vue.
   createApp({
      data() {
         return {
            name:'',
            maxAmount: 0,
            payments: 0
         }
      },
      created() {


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
               text: "Do you want to create a new loan?",
               icon: 'warning',
               showCancelButton: true,
               confirmButtonColor: '#3085d6',
               cancelButtonColor: '#d33',
               confirmButtonText: "Yes, I'm sure!",
               showCloseButton: true,
            }).then((result) => {
               if (result.isConfirmed) {
                  axios.post("/api/admin/loans", `name=${this.name}&maxAmount=${this.maxAmount}&payments=${this.payments}`)
                     .then(result => {
                        this.maxAmount = 0
                        this.name = ''
                        this.payments = 0
                     })
                     .then(response => {
                        Swal.fire({
                           position: 'top-end',
                           icon: 'success',
                           title: 'Loan created successfully',
                           showConfirmButton: false,
                           timer: 2500,
                           timerProgressBar: true,
                           toast: true,
                        })
                     })
                     .then(() => new Promise(resolve => setTimeout(resolve, 2500)))
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
         emptyData() {
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
