<template>
    <div class="dropdown dropend">
        <button type="button" class="btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"
            data-bs-auto-close="outside">
            <fa class="display-4" icon="user-shield"></fa>
        </button>
        <div class="dropdown-menu fadeIn">
            <div class="container text-center p-2">
                <div>
                    <button type="button" class="btn text-dark mb-3" style="background-color: #EBD671;">Reset
                        database</button>
                </div>
                <div>
                    <button type="button" class="btn btn-danger" style="background-color: #EB5353;">Flush
                        database</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {

}
</script>

<style scoped>
@keyframes fadeIn {
    from {
        opacity: 0%;
        translate: 0px 50px;
    }

    to {
        opacity: 100%;
        translate: 0px 0px;
    }
}

.fadeIn {
    animation: fadeIn 0.3s;
}
</style>