<template>
    <div class="dropdown dropend">
        <button type="button" class="btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"
            data-bs-auto-close="outside">
            <fa class="display-4" icon="filter"></fa>
        </button>
        <form @submit.prevent="handleSearch" class="col-sm-5 col-lg-4 col-xl-3 position-fixed dropdown-menu p-3 fadeIn">
            <div class="form-group row">
                <label for="inputBrand" class="me-2 col-sm-2 col-form-label">Brand:</label>
                <div class="col-sm-9">
                    <input v-model="filters.brand" type="text" class="form-control" id="inputBrand">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputModel" class="me-2 col-sm-2 col-form-label">Model:</label>
                <div class="col-sm-9">
                    <input v-model="filters.model" type="text" class="form-control" id="inputModel">
                </div>
            </div>
            <div v-if="admin" class="form-group row">
                <label for="inputRegion" class="me-2 col-sm-2 col-form-label">Region:</label>
                <div class="col-sm-9">
                    <select v-model="filters.region" class="mt-2" id="regionSelect">
                        <option value="" disabled selected>Select a region</option>
                        <option v-for="region in regions" :key="region.id" :value=region.name>{{ region.name }}</option>
                        <option value="">All</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="stockRangeInput">Stock:</label>
                <MultiRangeSlider id="stockRangeInput" baseClassName="multi-range-slider-bar-only"
                    :minValue="filters.stockLow" :maxValue="filters.stockTop" :max="50" :min="0" :step="1"
                    :rangeMargin="1" @input="updateStockRange" />
                <div class="d-flex justify-content-between">
                    <input v-model="filters.stockLow" class="text-end" readonly min="0" max="50" type="number"
                        style="width: 55px;">
                    <input v-model="filters.stockTop" class="text-end" readonly min="0" max="50" type="number"
                        style="width: 55px;">
                </div>
            </div>
            <button class="btn btn-primary my-2" type="submit">Search</button>
            <button class="btn btn-secondary ms-2 my-2" @click="clearFilters"
                type="button">Clear</button>
        </form>
    </div>
</template>

<script>
import axios from '@/http-common';
import MultiRangeSlider from "multi-range-slider-vue";
import "../../node_modules/multi-range-slider-vue/MultiRangeSliderBarOnly.css"

export default {
    props: {
        admin: Boolean
    },
    data() {
        return {
            filters: {
                brand: "",
                model: "",
                region: "",
                stockLow: 0,
                stockTop: 50,
            },
            regions: [],
        }
    },
    async created() {
        axios
            .get("/regions")
            .then((result) => {
                this.regions = result.data;
            })
            .catch((error) => console.log(error));
    },
    methods: {
        updateStockRange(e) {
            this.filters.stockLow = e.minValue;
            this.filters.stockTop = e.maxValue;
        },
        clearFilters() {
            this.filters.brand = "";
            this.filters.model = "";
            this.filters.region = "";
            this.filters.stockLow = 0;
            this.filters.stockTop = 50;
            this.emitter.emit("cars-filter-changed", this.filters);
        },
        handleSearch() {
            this.emitter.emit("cars-filter-changed", this.filters);
        }
    },
    components: {
        MultiRangeSlider
    }
};
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