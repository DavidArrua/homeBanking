let id = new URLSearchParams(window.location.search).get("id")

const app = Vue.
    createApp({
        data() {
            return {
                clients: [],
                accounts: [],
                accountsId: [],
                transactions: [],
                from: null,
                thru: null,
            }
        },
        created() {
            axios.get("/api/clients/current")
                .then(response => {
                    this.clients = response.data
                    this.accounts = this.clients.accounts
                    this.accountsId = this.accounts.find(account => account.id == id)
                    this.transactions = this.accountsId.transaction
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        methods: {
            formattedDate(date) {
                return date = new Date(date).toLocaleDateString()
            },
            formattedDateHours(date) {
                return date = new Date(date).toLocaleTimeString()
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
            formattedNumber(balance) {
                return balance = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(balance)
            },
            pdf() {
                Swal.fire({
                    title: 'Are you sure?',
                    text: "Do you want to download the account details in pdf format?",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: "Yes, I'm sure!",
                    showCloseButton: true,
                }).then((result) => {
                    if (result.isConfirmed) {
                        this.thru = new Date(this.thru).toISOString();
                        this.from = new Date(this.from).toISOString();
                        axios.post("/api/pdf",
                            {
                                "from": `${this.from}`,
                                "thru": `${this.thru}`,
                                "account": `${this.accountsId.number}`
                            })
                            .then(response => {
                                Swal.fire({
                                    position: 'top-end',
                                    icon: 'success',
                                    title: 'Successful download',
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
            }






            /* {
                this.thru = new Date(this.thru).toISOString();
                this.from = new Date(this.from).toISOString();

                axios.post("/api/pdf",
                {
                    "from":`${this.from}`,
                    "thru":`${this.thru}`,
                    "account":`${this.accountsId.number}`
                })
            } */
        },
    }).mount('#app');


