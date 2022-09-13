const app = Vue.
createApp({
    data() {
        return {
            clients: [],
            name: "",
            last: "",
            email: "",
        }
    },
    created(){
        this.getData()
    },
    methods: {
            getData(){
                axios.get('/api/clients')
                .then(response => {
                    this.clients = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
            },
            addClient(){
                axios.post('/rest/clients', {
                    firstName: this.name,
                    lastName: this.last,
                    email: this.email,
                })
                .then(()=> this.getData)
                .catch(function (error) {
                    console.log(error);
                }
                );
            }
    },
}).mount('#app');

