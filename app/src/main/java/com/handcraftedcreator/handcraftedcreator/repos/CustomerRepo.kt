package com.handcraftedcreator.handcraftedcreator.repos


import com.example.handcraftedmarket.repos.DatabaseRepo
import com.handcraftedcreator.handcraftedcreator.model.Customer
import com.handcraftedcreator.handcraftedcreator.model.CustomerDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.inject

class CustomerRepo: DatabaseRepo<Customer>() {

    private val customerDao: CustomerDao by inject()

    override suspend fun checkCache(): Customer? {
        return customerDao.getCustomer()
    }

    public override suspend fun storeToCache(t: Customer) {
        customerDao.insert(t)
    }

    override suspend fun listenToDb(): Flow<RepoResource<Customer>> {
        return customerDao.listenToCustomer().map { customer ->
            RepoResource(data = customer)
        }
    }
}