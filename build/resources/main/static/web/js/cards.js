const app = Vue.
   createApp({
      data() {
         return {
            cards: [],
            unfilteredCards:[],
            togle: false,
            dateNow: '',
            cardDelete: '',
         }
      },
      created() {
         
         this.getData();
         this.dateNow = (new Date(Date.now()).toISOString())

      },
      methods: {
         getData(){
            axios.get("/api/clients/current")
            .then(response => {
               this.unfilteredCards =response.data.cards
               this.cards = this.unfilteredCards.filter(card => card.stateOfCard == true);
            })
            .catch(function (error) {
               console.log(error);
            })
         },
         formattedCardDate(date) {
            return date = new Date(date).toLocaleDateString('es-AR', { month: '2-digit', year: '2-digit' });
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
         togleButton() {
            this.togle = !this.togle
         },
         showNumber(number) {
            if (this.togle == false) {
               start = number.slice(0, 15)
               end = number.slice(14, 19)
               replace = start.replace(start, '**** **** ****')
               return replace + end
            } else {
               return number
            }
         },
         alertMaxCard() {
            Swal.fire({
               position: 'top-end',
               icon: 'error',
               title: 'You have the maximum number of cards',
               showConfirmButton: false,
               timer: 2500,
               timerProgressBar: true,
               toast: true,
               showCloseButton: true,
            })
         },
         DateComparation(value) {
            return value = new Date(value).toLocaleDateString()
         },
         deleteCard() {
            Swal.fire({
               title: 'Are you sure?',
               text: "Do you want to delete an card?",
               icon: 'warning',
               showCancelButton: true,
               confirmButtonColor: '#3085d6',
               cancelButtonColor: '#d33',
               confirmButtonText: "Yes, I'm sure!",
               showCloseButton: true,
            }).then((result) => {
               if (result.isConfirmed) {
                  axios.patch('/api/clients/current/cards/state', `number=${this.cardDelete}`)
                     .then(response => {
                        Swal.fire({
                           position: 'top-end',
                           icon: 'success',
                           title: 'Card deleted successfully',
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
         }
      },
   }).mount('#app');
