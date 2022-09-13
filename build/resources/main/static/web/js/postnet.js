const app = Vue.
   createApp({
      data() {
         return {
            cards: [],
            unfilteredCards:[],
            description: '',
            amount: 0,
            id: null,
            cardFilter:{}
         }
      },
      created() {

         axios.get("/api/clients/current")
         .then(response => {
            this.unfilteredCards = response.data.cards
            this.cards = this.unfilteredCards.filter(card => card.stateOfCard == true);
         })
         .catch(function (error) {
            console.log(error);
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
         postnet(){
            Swal.fire({
               title: 'Are you sure?',
               text: "Do you want to make the payment?",
               icon: 'warning',
               showCancelButton: true,
               confirmButtonColor: '#3085d6',
               cancelButtonColor: '#d33',
               confirmButtonText: "Yes, I'm sure!",
               showCloseButton: true,
            }).then((result) => {
               if (result.isConfirmed) {
                  this.cardFilter = this.cards.find(card => card.id == this.id)
                  axios.post("/api/clients/current/transactions/payments",{
                     "id": `${this.id}`,
                     "cardHolder": `${this.cardFilter.cardHolder}`,
                     "number":`${this.cardFilter.number}`, 
                     "amount": `${this.amount}`,
                     "cvv": `${this.cardFilter.cvv}`, 
                     "thruDate": `${this.cardFilter.thruDate}`, 
                     "description": `${this.description}`
                  })
                  .then(result =>{
                     this.amount = 0
                     this.description =''
                  })
                     .then(response => {
                        Swal.fire({
                           position: 'top-end',
                           icon: 'success',
                           title: 'Successful pay',
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
         
      },
   }).mount('#app');
