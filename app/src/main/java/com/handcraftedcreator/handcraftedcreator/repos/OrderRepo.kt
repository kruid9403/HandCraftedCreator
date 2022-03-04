package com.handcraftedcreator.handcraftedcreator.repos


import com.example.handcraftedmarket.repos.DatabaseRepo
import com.handcraftedcreator.handcraftedcreator.model.Order
import com.handcraftedcreator.handcraftedcreator.model.OrderDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.inject

class OrderRepo: DatabaseRepo<Order>() {

    private val orderDao: OrderDao by inject()

    override suspend fun checkCache(): Order? {
        return orderDao.getOrder()
    }

    public override suspend fun storeToCache(t: Order) {
        orderDao.insert(t)
    }

    override suspend fun listenToDb(): Flow<RepoResource<Order>> {
        return orderDao.listenToOrder().map { order ->
            RepoResource(data = order)
        }
    }
}