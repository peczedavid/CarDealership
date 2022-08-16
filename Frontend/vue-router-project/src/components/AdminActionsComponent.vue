<template>
    <div class="dropdown dropend">
        <button type="button" class="btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"
            data-bs-auto-close="outside">
            <fa class="display-4" icon="user-shield"></fa>
        </button>
        <div class="dropdown-menu fadeIn">
            <div class="container p-2">
                <div class="mb-3">
                    <button type="button" class="btn btn-warning">Reset database</button>
                </div>
                <div>
                    <button type="button" class="btn btn-danger">Flush database</button>
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