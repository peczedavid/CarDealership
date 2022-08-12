<template>
    <div class="dropdown">
        <button type="button" class="btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"
            data-bs-auto-close="outside">
            <fa class="display-4" icon="user-shield"></fa>
        </button>
        <div class="dropdown-menu">
            <button class="btn text-dark" style="background-color: #EBD671;">Reset database</button>
            <button class="btn btn-danger" style="background-color: #EB5353;">Flush database</button>
        </div>
    </div>
</template>

<script>
export default {

}
</script>

<style>
</style>