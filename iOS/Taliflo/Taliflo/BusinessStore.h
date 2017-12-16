//
//  TLFBusinessStore.h
//  taliflo
//
//  Created by NR-Mac on 1/27/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface BusinessStore : NSObject

@property (nonatomic, strong) NSMutableArray *businesses;

+ (instancetype)getInstance;

@end
