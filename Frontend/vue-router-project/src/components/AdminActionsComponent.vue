<template>
    <div class="dropdown dropend">
        <button type="button" class="btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"
            data-bs-auto-close="outside">
            <fa class="display-4" icon="user-shield"></fa>
        </button>
        <div class="dropdown-menu fadeIn">
            <button class="btn text-dark" style="background-color: #EBD671;">Reset database</button>
            <button class="btn btn-danger" style="background-color: #EB5353;">Flush database</button>
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
    }

    to {
        opacity: 100%;
    }
}

.fadeIn {
    animation: fadeIn 0.3s;
}
</style>