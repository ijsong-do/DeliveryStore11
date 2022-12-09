
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import OrderManager from "./components/listers/OrderCards"
import OrderDetail from "./components/listers/OrderDetail"

import MenuView from "./components/MenuView"
import MenuViewDetail from "./components/MenuViewDetail"
import PaymentManager from "./components/listers/PaymentCards"
import PaymentDetail from "./components/listers/PaymentDetail"

import CustomerInfoManager from "./components/listers/CustomerInfoCards"
import CustomerInfoDetail from "./components/listers/CustomerInfoDetail"

import MyPageView from "./components/MyPageView"
import MyPageViewDetail from "./components/MyPageViewDetail"
import FoodCookingManager from "./components/listers/FoodCookingCards"
import FoodCookingDetail from "./components/listers/FoodCookingDetail"

import DeliveryManager from "./components/listers/DeliveryCards"
import DeliveryDetail from "./components/listers/DeliveryDetail"

import DeliveryListView from "./components/DeliveryListView"
import DeliveryListViewDetail from "./components/DeliveryListViewDetail"

export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/orders',
                name: 'OrderManager',
                component: OrderManager
            },
            {
                path: '/orders/:id',
                name: 'OrderDetail',
                component: OrderDetail
            },

            {
                path: '/menus',
                name: 'MenuView',
                component: MenuView
            },
            {
                path: '/menus/:id',
                name: 'MenuViewDetail',
                component: MenuViewDetail
            },
            {
                path: '/payments',
                name: 'PaymentManager',
                component: PaymentManager
            },
            {
                path: '/payments/:id',
                name: 'PaymentDetail',
                component: PaymentDetail
            },

            {
                path: '/customerInfos',
                name: 'CustomerInfoManager',
                component: CustomerInfoManager
            },
            {
                path: '/customerInfos/:id',
                name: 'CustomerInfoDetail',
                component: CustomerInfoDetail
            },

            {
                path: '/myPages',
                name: 'MyPageView',
                component: MyPageView
            },
            {
                path: '/myPages/:id',
                name: 'MyPageViewDetail',
                component: MyPageViewDetail
            },
            {
                path: '/foodCookings',
                name: 'FoodCookingManager',
                component: FoodCookingManager
            },
            {
                path: '/foodCookings/:id',
                name: 'FoodCookingDetail',
                component: FoodCookingDetail
            },

            {
                path: '/deliveries',
                name: 'DeliveryManager',
                component: DeliveryManager
            },
            {
                path: '/deliveries/:id',
                name: 'DeliveryDetail',
                component: DeliveryDetail
            },

            {
                path: '/deliveryLists',
                name: 'DeliveryListView',
                component: DeliveryListView
            },
            {
                path: '/deliveryLists/:id',
                name: 'DeliveryListViewDetail',
                component: DeliveryListViewDetail
            },


    ]
})
