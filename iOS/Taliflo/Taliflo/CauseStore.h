//
//  TLFCauseStore.h
//  taliflo
//
//  Created by NR-Mac on 1/27/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface CauseStore : NSObject

@property (nonatomic, strong) NSMutableArray* causes;

+ (instancetype)getInstance;

@end
