//
//  TLFVoucher.h
//  Taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TLFVoucher : NSObject

@property (nonatomic) int ID;
@property (nonatomic) int issuedByID;
@property (nonatomic, copy) NSString *issuedByName;
@property (nonatomic) int claimedByID;
@property (nonatomic, copy) NSString *claimedByName;
@property (nonatomic) int maxValue;
@property (nonatomic, getter=isRedeemed) BOOL redeemed;
@property (nonatomic, strong) NSDate *createdAt;
@property (nonatomic, strong) NSDate *updatedAt;

- (instancetype)initWithJSONValues:(NSDictionary *)jsonObject;

@end
